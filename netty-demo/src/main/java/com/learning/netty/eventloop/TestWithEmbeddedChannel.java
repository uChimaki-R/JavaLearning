package com.learning.netty.eventloop;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.nio.charset.Charset;

public class TestWithEmbeddedChannel {
    public static void main(String[] args) {
        EmbeddedChannel channel = new EmbeddedChannel(
                // 最大信息长度限制为1024字节，长度信息在0下标位置，长度信息长度为4字节，从长度信息结束后位置开始需要跳过2个字节后才是内容，最后的内容要截掉前面6个字节的内容
                new LengthFieldBasedFrameDecoder(1024, 0, 4, 2, 6),
                new LoggingHandler(LogLevel.DEBUG)
        );

        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        addMessage(buffer, "Hello World!");
        addMessage(buffer, "Netty!");
        channel.writeInbound(buffer);
    }

    private static void addMessage(ByteBuf buffer, String message) {
        // 这里面可以理解为一个最基本的协议，而 LengthFieldBasedFrameDecoder 的参数就是根据这个协议的结构来的，从而解决半包粘包问题
        // 构造消息
        byte[] bytes = message.getBytes(Charset.defaultCharset());
        // 长度信息用int存，刚好4字节
        buffer.writeInt(bytes.length);
        // 假设中间有两个字节的版本信息
        buffer.writeBytes("v1".getBytes(Charset.defaultCharset()));
        buffer.writeBytes(bytes);
    }
}
