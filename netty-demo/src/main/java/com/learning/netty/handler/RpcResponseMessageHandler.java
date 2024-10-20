package com.learning.netty.handler;

import com.learning.netty.eventloop.RpcClientManager;
import com.learning.netty.protocol.message.RpcResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RpcResponseMessageHandler extends SimpleChannelInboundHandler<RpcResponseMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponseMessage rpcResponseMessage) throws Exception {
        Promise<Object> promise = RpcClientManager.PROMISES.remove(rpcResponseMessage.getSequenceId());
        if (promise == null) {
            throw new Exception("序列id [" + rpcResponseMessage.getSequenceId() + "]" + " 对应的 Promise 不存在");
        }
        if (rpcResponseMessage.getSuccess()) {
            promise.setSuccess(rpcResponseMessage.getResult());
            log.info("成功获取到结果: {}", rpcResponseMessage.getResult());
        } else {
            promise.setFailure(new Exception(rpcResponseMessage.getError()));
            log.warn("调用出现异常, 异常信息: {}", rpcResponseMessage.getError());
        }
    }
}
