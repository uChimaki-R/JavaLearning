package nettyT;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import nettyT.messages.RpcRequestMsg;
import nettyT.utils.IdGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class RpcClientManager {
    static final Map<Integer, Promise<Object>> MESSAGE_ID_TO_PROMISE_MAP = new ConcurrentHashMap<>();

    public static <T> T getProxy(Class<T> serviceClass) {
        return serviceClass.cast(
                Proxy.newProxyInstance(
                        serviceClass.getClassLoader(),
                        new Class[]{serviceClass},
                        new InvocationHandler() {
                            @Override
                            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                                RpcRequestMsg rpcRequestMsg = new RpcRequestMsg(
                                        serviceClass.getName(),
                                        method.getName(),
                                        method.getParameterTypes(),
                                        args
                                );
                                // 设置唯一id
                                int messageId = IdGenerator.nextId();
                                rpcRequestMsg.setMessageId(messageId);
                                // 创建Promise来接受结果
                                Promise<Object> promise = new DefaultPromise<>(getChannel().eventLoop());
                                MESSAGE_ID_TO_PROMISE_MAP.put(messageId, promise);
                                // 发送rpc请求
                                getChannel().writeAndFlush(rpcRequestMsg).addListener(future -> {
                                    if (!future.isSuccess()) {
                                        log.warn("发送远程调用失败: {}", future.cause().getMessage());
                                    }
                                });
                                promise.await();
                                if (promise.isSuccess()) {
                                    return promise.getNow();
                                } else {
                                    throw promise.cause();
                                }
                            }
                        }
                )
        );
    }

    private static Channel rpcChannel;

    private static Channel getChannel() {
        if (rpcChannel != null) {
            return rpcChannel;
        }
        synchronized (RpcClientManager.class) {
            if (rpcChannel != null) {
                return rpcChannel;
            }
            initChannel();
            return rpcChannel;
        }
    }

    private static void initChannel() {
        EventLoopGroup group = new NioEventLoopGroup(1);
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO))
                                .addLast(new DemoMsgCodec())
                                .addLast(new ClientDemoMsgHandler());
                    }
                });
        try {
            rpcChannel = bootstrap.connect("localhost", 9090).sync().channel();
            rpcChannel.closeFuture().addListener(future -> {
                group.shutdownGracefully();
            });
            log.info("channel 初始化成功");
        } catch (Exception e) {
            log.error("channel 初始化失败", e);
        }
    }
}
