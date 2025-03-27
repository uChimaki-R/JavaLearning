package nettyT;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;
import nettyT.messages.DemoMsg;
import nettyT.messages.RpcRequestMsg;
import nettyT.messages.RpcResponseMsg;

import java.lang.reflect.Method;

@Slf4j
public class ServerDemoMsgHandler extends SimpleChannelInboundHandler<DemoMsg> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端连接: {}", ctx.channel().remoteAddress());
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, DemoMsg demoMsg) throws Exception {
        log.info("收到客户端[{}]消息: {}", ctx.channel().remoteAddress(), demoMsg);
        if (demoMsg instanceof RpcRequestMsg) {
            RpcRequestMsg rpcRequestMsg = (RpcRequestMsg) demoMsg;
            // 获取接口
            Class<?> interfaceClass = Class.forName(rpcRequestMsg.getInterfaceName());
            // 获取接口方法
            Method method = interfaceClass.getDeclaredMethod(rpcRequestMsg.getMethodName(), rpcRequestMsg.getParameterTypes());
            // 获取接口对应的实例
            // 如果是spring可以扫描包内标了自定义注解的bean保存到map里获取，这里直接写死map获取
            Object instance = Server.interfaceToInstance.get(interfaceClass);
            // 执行方法
            RpcResponseMsg responseMsg = new RpcResponseMsg();
            // 回填对应的messageId
            responseMsg.setMessageId(rpcRequestMsg.getMessageId());
            try {
                Object result = method.invoke(instance, rpcRequestMsg.getParams());
                responseMsg.setResult(result);
            } catch (Exception e) {
                log.warn("[调用错误]调用信息: {} 错误: {}", rpcRequestMsg, e.getCause().getMessage());
                responseMsg.setExceptionMessage(e.getCause().getMessage());
            }
            // 返回方法结果
            ctx.writeAndFlush(responseMsg);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("客户端断开连接: {}", ctx.channel().remoteAddress());
//        ctx.close();
    }
}
