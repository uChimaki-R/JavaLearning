package com.learning.netty.handler;

import com.learning.netty.protocol.message.LoginRequestMessage;
import com.learning.netty.protocol.message.LoginResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class LoginRequestMessageHandler extends SimpleChannelInboundHandler<LoginRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginRequestMessage loginRequestMessage) throws Exception {
        log.info("接收到登录请求 {}", loginRequestMessage);
        LoginResponseMessage loginResponseMessage = new LoginResponseMessage(0, "zhangsan".equals(loginRequestMessage.getUsername()) && "123".equals(loginRequestMessage.getPassword()));
        log.info("返回登录结果 {}", loginResponseMessage);
        channelHandlerContext.writeAndFlush(loginResponseMessage);
    }
}
