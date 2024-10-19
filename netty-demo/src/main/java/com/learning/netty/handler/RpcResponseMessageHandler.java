package com.learning.netty.handler;

import com.learning.netty.protocol.message.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponseMessage rpcResponseMessage) throws Exception {
        if (rpcResponseMessage.getSuccess()) {
            log.info("成功获取到结果: {}", rpcResponseMessage.getResult());
        } else {
            log.warn("调用出现异常, 异常信息: {}", rpcResponseMessage.getError());
        }
    }
}
