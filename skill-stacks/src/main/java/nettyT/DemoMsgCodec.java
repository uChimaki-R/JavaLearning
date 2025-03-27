package nettyT;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import nettyT.messages.DemoMsg;
import nettyT.properties.RpcProperties;
import nettyT.utils.SerializeAlgorithm;

import java.util.List;

public class DemoMsgCodec extends MessageToMessageCodec<ByteBuf, DemoMsg> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, DemoMsg demoMsg, List<Object> list) throws Exception {
        ByteBuf buffer = channelHandlerContext.alloc().buffer();
        // 编解码类型
        SerializeAlgorithm algorithm = RpcProperties.getSerializeAlgorithm();
        buffer.writeInt(algorithm.ordinal());
        // 消息类型
        buffer.writeInt(demoMsg.getMessageType());
        // 将msg序列化
        byte[] msgBytes = algorithm.encode(demoMsg);
        // 写入长度
        buffer.writeInt(msgBytes.length);
        // 写入内容
        buffer.writeBytes(msgBytes);
        list.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        while (true) { // while true 模拟 LengthFieldBasedFrameDecoder 解决半包粘包问题
            // 没有 解码类型+消息类型+长度信息 的长度
            if (byteBuf.readableBytes() < Integer.SIZE / 8 * 3) return;
            // 标记起始位置，后面数据没来全的时候需要回退回来
            byteBuf.markReaderIndex();
            // 依次读取 解码类型 消息类型 长度信息
            int decodeType = byteBuf.readInt();
            int messageType = byteBuf.readInt();
            // 读取内容长度
            int length = byteBuf.readInt();
            if (byteBuf.readableBytes() < length) {
                // 后续内容未到
                byteBuf.resetReaderIndex();
                return;
            }
            byte[] messageBytes = new byte[length];
            byteBuf.readBytes(messageBytes);
            // 根据解码类型获取解码器
            SerializeAlgorithm decoder = SerializeAlgorithm.values()[decodeType];
            // 构造对象
            DemoMsg message = decoder.decode(messageBytes, DemoMsg.getMsgClass(messageType));
            list.add(message);
        }
    }
}
