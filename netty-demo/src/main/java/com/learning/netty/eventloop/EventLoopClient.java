package com.learning.netty.eventloop;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

import java.net.InetSocketAddress;
import java.nio.charset.Charset;

public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        Channel channel = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline().addLast(new StringEncoder());
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 8080))
                .sync()
                .channel();
        channel.writeAndFlush(Charset.defaultCharset().encode("Hello World"));
        // 记得将断点类型改为Thread，因为使用的是线程池，发送消息的不是在当前线程，而断点默认是全局断点，会让发送消息的线程也停下，从而导致发不出去
        System.out.println("breakpoint");
    }
}
