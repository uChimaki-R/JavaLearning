package com.learning.netty.handler;

import com.learning.netty.protocol.message.RpcRequestMessage;
import com.learning.netty.protocol.message.RpcResponseMessage;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.lang.reflect.Method;
import java.util.ServiceLoader;

@ChannelHandler.Sharable
public class RpcRequestMessageHandler extends SimpleChannelInboundHandler<RpcRequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequestMessage rpcRequestMessage) throws Exception {
        // 找到所有实现类
        ServiceLoader<?> impls = ServiceLoader.load(Class.forName(rpcRequestMessage.getInterfaceName()));
        if (impls.iterator().hasNext()) {
            try {
                // 获取实现类
                Object impl = impls.iterator().next();
                // 获取方法
                Method method = impl.getClass().getMethod(rpcRequestMessage.getMethodName(), rpcRequestMessage.getParameterTypes());
                // 执行方法
                Object result = method.invoke(impl, rpcRequestMessage.getParams());
                channelHandlerContext.writeAndFlush(new RpcResponseMessage(rpcRequestMessage.getSequenceId(), true, null, result));
            } catch (Exception e) {
                channelHandlerContext.writeAndFlush(new RpcResponseMessage(rpcRequestMessage.getSequenceId(), false, "调用出错: " + e.getCause().getMessage(), null));
            }
        } else {
            channelHandlerContext.writeAndFlush(new RpcResponseMessage(rpcRequestMessage.getSequenceId(), false, "没有找到实现类", null));
        }
    }
}
