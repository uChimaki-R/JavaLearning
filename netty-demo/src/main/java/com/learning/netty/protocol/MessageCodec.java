package com.learning.netty.protocol;

import com.learning.netty.config.Config;
import com.learning.netty.protocol.domain.Message;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

// public class MessageCodec extends ByteToMessageCodec<Message>
// 如果继承 ByteToMessageCodec 类的话是无法加 @Sharable 注解的，即无法多个 channel 共用一个 MessageCodec 对象
// 因为 ByteToMessageCodec 认为传入的 Byte 数组不一定是一个完整的包，可能会使用对象内的缓存来存储部分字节数据，直到达成一个完整的包供解析
// 而这个使用对象内的缓存的行为会造成线程不安全，如一个线程存了不到一个包的信息，等待着后续信息的到来，但是此时另一个线程获取到了信息，存到了同一个缓存位置（同一对象）
// 然后后者就组成了一个完整的包，开始了 decode 解析，这明显是错误的，所以 ByteToMessageCodec 使用了检查机制，不允许子类加 @Sharable 注解
// 而 MessageToMessageCodec 明确了接收到了就是一个 Message，意味着该 handler 之前肯定有一个处理半包粘包问题的 handler，如 LengthFieldBasedFrameDecoder，这部分需要我们自己来保证
// 所以我们加上 @Sharable 注解的同时，需要谨记一定要在这个 handler 前加上半包粘包处理器，这样才能将 MessageToMessageCodec 的对象公用

/**
 * 自定义的简单协议编码解码器
 * 如若需要共用该类的实例化对象作为 handler，请确保该 handler 之前存在半包粘包处理器
 */
@Slf4j
@ChannelHandler.Sharable
public class MessageCodec extends MessageToMessageCodec<ByteBuf, Message> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Message message, List<Object> list) throws Exception {
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        // 4字节 魔数，快速判断是否无效数据包
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        // 1字节 协议版本号
        buffer.writeByte(1);
        // 1字节 序列化方式（如使用0标记为jdk，1标记为json）
        Algorithm algorithm = Config.getAlgorithm();
        log.info("algorithm: {}", algorithm);
        buffer.writeByte(algorithm.ordinal());
        // 1字节 信息类型
        buffer.writeByte(message.getType());
        // 4字节 信息的序列号（信息可能是分割异步发送的，异步接收后需要根据序列号重组消息）
        buffer.writeInt(message.getSequenceId());

        // 注意：上面字节数为11，加上后面4位的长度信息，固定长度信息为15个字节，不是2的n次方倍，内存没有对齐，效率会下降
        // 需要使用无意义的字节填充对齐
        buffer.writeByte(0xff);

        // 数据部分
        byte[] bytes = algorithm.serialize(message);
        // 4字节 长度信息
        buffer.writeInt(bytes.length);
        // 数据内容
        buffer.writeBytes(bytes);
        // 传递给下一个handler
        list.add(buffer);
        log.info("发送信息 {}", buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int magicNumber = byteBuf.readInt();

        byte version = byteBuf.readByte();

        byte serializationType = byteBuf.readByte();

        int sequenceId = byteBuf.readInt();

        int messageType = byteBuf.readByte();

        byteBuf.readByte();  // 无效字节

        int length = byteBuf.readInt();

        ByteBuf buffer = channelHandlerContext.alloc().heapBuffer(length);
        byteBuf.readBytes(buffer);
        Algorithm algorithm = Algorithm.values()[serializationType];
        log.info("algorithm: {}", algorithm);
        Message message = algorithm.deserialize(buffer.array(), Message.class);

        list.add(message);
        log.info("magicNumber: {}, version: {}, serializationType: {}, sequenceId: {}, messageType: {}", magicNumber, version, serializationType, sequenceId, messageType);
        log.info("接收信息: {}", message);
    }
}
