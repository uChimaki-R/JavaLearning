package rocketMQ.simple;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class SimpleConsumer {
    public static void main(String[] args) {
        // Push被动等待nameserver发送消息，Pull主动从nameserver拉取信息
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("simple_consumer");
        consumer.setNamesrvAddr("192.168.202.135:9876");
        try {
            consumer.subscribe("LOG_TOPIC", "*"); // 订阅simple主题下所有tag类型的消息
            consumer.registerMessageListener(new MessageListenerConcurrently() {
                @Override
                public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                    list.forEach((message) -> {
                        System.out.println("收到消息: " + new String(message.getBody(), StandardCharsets.UTF_8));
                    });
                    // 返回成功标志
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
            });
            // 注册好监听后才start
            consumer.start();
            System.out.println("Consumer 启动成功");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            // 一直监听即可，不关闭
//            consumer.shutdown();
        }
    }
}
