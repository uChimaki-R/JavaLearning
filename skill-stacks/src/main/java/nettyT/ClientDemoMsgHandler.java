package nettyT;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.Promise;
import lombok.extern.slf4j.Slf4j;
import nettyT.messages.RpcResponseMsg;

@Slf4j
public class ClientDemoMsgHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("连接成功，发送连接成功消息");
//        RpcRequestMsg rpcRequestMsg = new RpcRequestMsg(
//                "nettyDemo.rpc.interfaces.HelloInterface",
//                "sayHello",
//                new Class[]{String.class},
//                new Object[]{"Zhang san"}
//        );
//        // 设置唯一id
//        rpcRequestMsg.setMessageId(IdGenerator.nextId());
//        ctx.writeAndFlush(rpcRequestMsg).addListener(future -> {
//            if (!future.isSuccess()) {
//                log.warn("发送远程调用失败: {}", future.cause().getMessage());
//                ctx.close();
//            }
//        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        log.info("收到服务端消息: {}", msg);
        if (msg instanceof RpcResponseMsg) {
            // 填充Promise结果
            RpcResponseMsg rpcResponseMsg = (RpcResponseMsg) msg;
            Integer messageId = rpcResponseMsg.getMessageId();
            Promise<Object> promise = RpcClientManager.MESSAGE_ID_TO_PROMISE_MAP.remove(messageId);
            if (rpcResponseMsg.getExceptionMessage() == null) {
                promise.setSuccess(rpcResponseMsg.getResult());
            } else {
                promise.setFailure(new Exception(rpcResponseMsg.getExceptionMessage()));
            }
        }
    }
}
