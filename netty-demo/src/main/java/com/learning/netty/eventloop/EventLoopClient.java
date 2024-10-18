package com.learning.netty.eventloop;

import com.learning.netty.protocol.MessageCodec;
import com.learning.netty.protocol.domain.LoginRequestMessage;
import com.learning.netty.protocol.domain.LoginResponseMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
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
                                .addLast(new LengthFieldBasedFrameDecoder(1024, 12, 4))
                                .addLast(new MessageCodec())
                                .addLast(new ChannelInboundHandlerAdapter() {
                                    @Override
                                    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                        if (msg instanceof LoginResponseMessage) {
                                            LoginResponseMessage loginResponseMessage = (LoginResponseMessage) msg;
                                            log.info("获取到登陆结果 {}", loginResponseMessage);
                                            if (loginResponseMessage.getSuccess()) {
                                                LOGIN_SUCCESS.set(true);
                                            }
                                            LOGIN_LOCK.countDown();
                                        }
                                        super.channelRead(ctx, msg);
                                    }

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
                                            LoginRequestMessage loginRequestMessage = new LoginRequestMessage(username, password);
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
                                                log.info("...业务代码");
                                            } else {
                                                log.warn("账号或密码错误");
                                            }
                                            log.info("关闭连接");
                                            ctx.channel().close();
                                        }).start();
                                        super.channelActive(ctx);
                                    }
                                });
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
                log.info("channel closed");
            }
        });

        channel.closeFuture().sync();
    }
}
