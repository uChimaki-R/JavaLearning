package com.learning.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;

@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 8080))
                .sync()
                .channel();

        // 开启线程可以一直发送信息
        new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String line = scanner.nextLine();
                if ("quit".equalsIgnoreCase(line)) {
                    break;
                }
                channel.writeAndFlush(line);
            }
            // 发送完关闭通道
            channel.close();
        }).start();

        // 注册关闭后的逻辑
        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                // 需要把事件循环组关闭，主进程才会结束
                group.shutdownGracefully();
                log.info("channel closed");
            }
        });

        channel.closeFuture().sync();
    }
}
