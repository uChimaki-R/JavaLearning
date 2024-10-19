package com.learning.netty.eventloop;

import com.learning.netty.handler.LoginRequestMessageHandler;
import com.learning.netty.handler.QuitHandler;
import com.learning.netty.protocol.MessageCodec;
import com.learning.netty.protocol.MyFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {
        // 各channel可以公用的handler
        MessageCodec MESSAGE_CODEC = new MessageCodec();
        LoginRequestMessageHandler LOGIN_REQUEST_HANDLER = new LoginRequestMessageHandler();
        QuitHandler QUIT_HANDLER = new QuitHandler();

        ServerBootstrap bootstrap = new ServerBootstrap()
                .channel(NioServerSocketChannel.class)
                .group(new NioEventLoopGroup())
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
                                .addLast(new MyFrameDecoder())
                                .addLast(new LoggingHandler())
                                .addLast(MESSAGE_CODEC)
                                .addLast(new LoggingHandler())
                                .addLast(new IdleStateHandler(5, 0, 0))
                                .addLast(new ChannelDuplexHandler() {
                                    @Override
                                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
                                        IdleStateEvent event = (IdleStateEvent) evt;
                                        if (IdleState.READER_IDLE.equals(event.state())) {
                                            log.info("心跳信息间隔超过5秒, 断开连接");
                                            ctx.channel().close();
                                        }
                                    }
                                })
                                .addLast(LOGIN_REQUEST_HANDLER)
                                .addLast(QUIT_HANDLER);
                    }
                });
        bootstrap.bind(8080);
    }

}
