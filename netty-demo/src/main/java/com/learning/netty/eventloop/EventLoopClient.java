package com.learning.netty.eventloop;

import com.learning.netty.handler.RpcResponseMessageHandler;
import com.learning.netty.protocol.MessageCodec;
import com.learning.netty.protocol.MyFrameDecoder;
import com.learning.netty.protocol.message.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
public class EventLoopClient {
    public static void main(String[] args) throws InterruptedException {
        // 设置倒计时锁，让登录界面等待服务端返回结果
        CountDownLatch LOGIN_LOCK = new CountDownLatch(1);
        // 不同线程之间需要用原子类
        AtomicBoolean LOGIN_SUCCESS = new AtomicBoolean(false);
        NioEventLoopGroup group = new NioEventLoopGroup();
        Channel channel = new Bootstrap()
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
//                                .addLast(new IdleStateHandler(0, 3, 0))
//                                .addLast(new ChannelDuplexHandler() {
//                                    @Override
//                                    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
//                                        IdleStateEvent event = (IdleStateEvent) evt;
//                                        if (IdleState.WRITER_IDLE.equals(event.state())) {
//                                            // 太久没写出信息，发送心跳
//                                            ctx.writeAndFlush(new PingMessage(0, "zhangsan"));
//                                        }
//                                    }
//                                })
                                // 登录等
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                        log.info("连接成功，开始登录");
                                        // 连接进入登录界面，开启新线程处理登录逻辑
                                        new Thread(() -> {
                                            Scanner scanner = new Scanner(System.in);
                                            System.out.print("请输入账号: ");
                                            String username = scanner.nextLine();
                                            System.out.print("请输入密码: ");
                                            String password = scanner.nextLine();
                                            LoginRequestMessage loginRequestMessage = new LoginRequestMessage(0, username, password);
                                            ctx.writeAndFlush(loginRequestMessage);
                                            log.info("发送登录请求，等待登录结果");
                                            // 等待read事件接收到服务端的返回结果
                                            try {
                                                LOGIN_LOCK.await();
                                            } catch (InterruptedException e) {
                                                throw new RuntimeException(e);
                                            }
                                            if (LOGIN_SUCCESS.get()) {
                                                log.info("登陆成功");
                                                // 发送远程调用
                                                log.info("发送远程调用, 调用 sayHello 方法");
                                                ctx.writeAndFlush(new RpcRequestMessage(
                                                        0,
                                                        "com.learning.netty.basic.inter.Communicate",
                                                        "sayHello",
                                                        new Class[]{String.class},
                                                        new Object[]{username}
                                                ));
                                                log.info("发送远程调用, 调用 calcDiv 方法");
                                                ctx.writeAndFlush(new RpcRequestMessage(
                                                        1,
                                                        "com.learning.netty.basic.inter.Calculate",
                                                        "calcDiv",
                                                        new Class[]{Integer.class, Integer.class},
                                                        new Object[]{3, 0}  // 故意出错，用于测试
                                                ));
                                                log.info("发送远程调用, 调用 calcAdd 方法");
                                                ctx.writeAndFlush(new RpcRequestMessage(
                                                        1,
                                                        "com.learning.netty.basic.inter.Calculate",
                                                        "calcAdd",
                                                        new Class[]{Integer.class, Integer.class},
                                                        new Object[]{256, 256}
                                                ));
                                            } else {
                                                log.warn("账号或密码错误");
                                            }
//                                            log.info("关闭连接");
//                                            ctx.channel().close();
                                        }).start();
                                    }
                                })
                                // 登录响应信息处理
                                .addLast(new SimpleChannelInboundHandler<LoginResponseMessage>() {
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponseMessage loginResponseMessage) throws Exception {
                                        log.info("获取到登陆结果 {}", loginResponseMessage);
                                        if (loginResponseMessage.getSuccess()) {
                                            LOGIN_SUCCESS.set(true);
                                        }
                                        LOGIN_LOCK.countDown();
                                    }
                                })
                                // rpc调用响应信息处理
                                .addLast(new RpcResponseMessageHandler());
                    }
                })
                .connect(new InetSocketAddress("127.0.0.1", 8080))
                .sync()
                .channel();

        // 注册关闭后的逻辑
        channel.closeFuture().addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                // 需要把事件循环组关闭，主进程才会结束
                group.shutdownGracefully();
                LOGIN_LOCK.countDown();
                log.info("channel closed");
            }
        });
        channel.closeFuture().sync();
    }

}
