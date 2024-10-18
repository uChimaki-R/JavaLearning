package com.learning.netty.eventloop;

import com.learning.netty.config.Config;
import com.learning.netty.protocol.Algorithm;
import com.learning.netty.protocol.MessageCodec;
import com.learning.netty.protocol.MyFrameDecoder;
import com.learning.netty.protocol.domain.LoginRequestMessage;
import com.learning.netty.protocol.domain.Message;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class TestWithEmbeddedChannel {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new MyFrameDecoder(),
                new LoggingHandler(LogLevel.DEBUG),
                new MessageCodec()
        );

        channel.writeOutbound(new LoginRequestMessage("zhangsan", "123"));
        System.out.println("=====================================================================");
        ByteBuf buf = getInput(new LoginRequestMessage("zhangsan", "123"));
        channel.writeInbound(buf);
    }

    private static ByteBuf getInput(Message message) throws Exception {
        ByteBuf buffer = ByteBufAllocator.DEFAULT.buffer();
        buffer.writeBytes(new byte[]{1, 2, 3, 4});
        buffer.writeByte(1);
        Algorithm algorithm = Config.getAlgorithm();
        buffer.writeByte(algorithm.ordinal());
        buffer.writeByte(message.getType());
        buffer.writeInt(message.getSequenceId());
        buffer.writeByte(0xff);
        byte[] bytes = algorithm.serialize(message);
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        return buffer;
    }
}
