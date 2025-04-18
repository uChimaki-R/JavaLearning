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
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;

import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RpcClientManager {
    private static Channel channel;
    private static final Object LOCK = new Object();
    // 代理对象发送rpc请求时向map中添加序列id对应的promise对象并阻塞等待结果，handler处理完毕后向序列id对应的promise对象存入结果，让代理方法继续运行返回结果。
    public static final Map<Integer, Promise<Object>> PROMISES = new ConcurrentHashMap<>();

    public static void main(String[] args) {
//        getChannel().writeAndFlush(new RpcRequestMessage(
//                0,
//                "com.learning.netty.basic.inter.Communicate",
//                "sayHello",
//                new Class[]{String.class},
//                new Object[]{"zhangsan"}
//        ));
        Calculate calc = getProxy(Calculate.class);
        System.out.println("结果是: " + calc.calcAdd(256, 256));
        System.out.println("结果是: " + calc.calcDiv(1, 0));  // 故意出错
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
                            Integer sequenceId = SequenceIdGenerator.nextSequenceId();
                            getChannel().writeAndFlush(new RpcRequestMessage(
                                    sequenceId,
                                    clazz.getName(),
                                    method.getName(),
                                    method.getParameterTypes(),
                                    args
                            ));
                            // 存入Promise对象等待请求结果
                            // 定义Promise对象时参数需要一个线程对象，该线程的作用: 作为使用promise.addListener()定义异步获取结果的时候执行该异步逻辑的线程
                            // 因为这里是同步调用等待结果，所以实际上这个参数里的线程没有用到，但是他就是需要一个线程对象来初始化，这里使用channel的线程对象
                            DefaultPromise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
                            PROMISES.put(sequenceId, promise);
                            // 等待结果
                            promise.await();
                            if (promise.isSuccess()) {
                                return promise.getNow();
                            } else {
                                throw new Exception(promise.cause().getMessage());
                            }
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
