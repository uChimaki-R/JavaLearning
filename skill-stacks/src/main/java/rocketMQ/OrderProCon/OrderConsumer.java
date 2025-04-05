package rocketMQ.OrderProCon;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

public class OrderConsumer {
    public static void main(String[] args) {
        // 和一般消费者一样，只是传入的内部类使用顺序接口
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("order_consumers");
        consumer.setNamesrvAddr("192.168.202.135:9876");
        try {
            String topic = "order_topic";
            consumer.subscribe(topic, "*");
            // 使用顺序接口
            consumer.registerMessageListener(new MessageListenerOrderly() {
                @Override
                public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                    list.forEach(message -> {
                        System.out.println("获取到消息: " + new String(message.getBody()));
                    });
                    return ConsumeOrderlyStatus.SUCCESS;
                }
            });
            consumer.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
