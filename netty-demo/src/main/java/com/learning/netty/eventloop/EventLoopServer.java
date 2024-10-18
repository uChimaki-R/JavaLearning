package com.learning.netty.eventloop;

import com.learning.netty.handler.LoginRequestMessageHandler;
import com.learning.netty.protocol.MessageCodec;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        // 各channel可以公用的handler
        MessageCodec MESSAGE_CODEC = new MessageCodec();

        ServerBootstrap bootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
                                .addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4))
                                .addLast(MESSAGE_CODEC)
                                .addLast(new LoginRequestMessageHandler());
                    }
                });
        bootstrap.bind(8080);
    }

}
