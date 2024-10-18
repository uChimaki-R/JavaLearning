package com.learning.netty.eventloop;

import com.learning.netty.protocol.MessageCodec;
import com.learning.netty.protocol.domain.LoginRequestMessage;
import com.learning.netty.protocol.domain.LoginResponseMessage;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
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
                                .addLast(new SimpleChannelInboundHandler<LoginRequestMessage>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
                                            log.info("接收到登录请求 {}", loginRequestMessage);
                                            LoginResponseMessage loginResponseMessage = new LoginResponseMessage("zhangsan".equals(loginRequestMessage.getUsername()) && "123".equals(loginRequestMessage.getPassword()));
                                            log.info("返回登录结果 {}", loginResponseMessage);
                                            channelHandlerContext.writeAndFlush(loginResponseMessage);
                                    }
                                });
                    }
                });
        bootstrap.bind(8080);
    }
}
