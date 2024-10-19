package com.learning.netty.eventloop;

import com.learning.netty.basic.inter.Calculate;
import com.learning.netty.handler.RpcResponseMessageHandler;
import com.learning.netty.protocol.MessageCodec;
import com.learning.netty.protocol.MyFrameDecoder;
import com.learning.netty.protocol.SequenceIdGenerator;
import com.learning.netty.protocol.message.RpcRequestMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;

public class RpcClientManager {
    private static Channel channel;
    private static final Object LOCK = new Object();

    public static void main(String[] args) {
//        getChannel().writeAndFlush(new RpcRequestMessage(
//                0,
//                "com.learning.netty.basic.inter.Communicate",
//                "sayHello",
//                new Class[]{String.class},
//                new Object[]{"zhangsan"}
//        ));
        Calculate calc = getProxy(Calculate.class);
        calc.calcAdd(256, 256);
    }

    /**
     * 获取代理对象，拥有的接口和传入的接口一致，但是将内部的实现改为发送rpc请求来获取结果
     */
    public static <T> T getProxy(Class<T> clazz) {
        // 获取代理对象，把对象转回T类
        return clazz.cast(
                Proxy.newProxyInstance(
                        clazz.getClassLoader(),
                        new Class[]{clazz},
                        (proxy, method, args) -> {
                            getChannel().writeAndFlush(new RpcRequestMessage(
                                    SequenceIdGenerator.nextSequenceId(),
                                    clazz.getName(),
                                    method.getName(),
                                    method.getParameterTypes(),
                                    args
                            ));
                            // 暂时先只发送信息，不接收结果
                            return null;
                        })
        );
    }

    /**
     * 双检索单例模式
     */
    public static Channel getChannel() {
        if (channel != null) {
            return channel;
        }
        synchronized (LOCK) {
            // 里面也要判断，防止下面的情况：
            // 一开始有多个线程到达 synchronized 这个位置，但是只有一个能够进入，进入后初始化了channel
            // 如果没有再次判断非空，后一个线程进入的时候会再次执行初始化操作，导致非单例
            if (channel != null) {
                return channel;
            }
            initChannel();
            return channel;
        }
    }

    static void initChannel() {
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap()
                .channel(NioSocketChannel.class)
                .group(group)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        nioSocketChannel.pipeline()
                                .addLast(new MyFrameDecoder())
                                .addLast(new LoggingHandler())
                                .addLast(new MessageCodec())
                                .addLast(new LoggingHandler())
                                // rpc调用响应信息处理
                                .addLast(new RpcResponseMessageHandler());
                    }
                });
        try {
            channel = bootstrap.connect(new InetSocketAddress("127.0.0.1", 8080)).sync().channel();
            // 注册关闭后的逻辑
            channel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
            // 需要外部手动关闭channel，不应该在这里阻塞等待结束
//            channel.closeFuture().sync();
        } catch (Exception e) {
            group.shutdownGracefully();
            throw new RuntimeException(e);
        }

    }
}
