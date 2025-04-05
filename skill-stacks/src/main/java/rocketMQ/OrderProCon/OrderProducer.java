package rocketMQ.OrderProCon;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class OrderProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("order_producers");
        producer.setNamesrvAddr("192.168.202.135:9876");
        try {
            producer.start();
            String topic = "order_topic";
            for (int i = 0; i < 10; i++) {
                // i次发送，要求单批发送，所有的j按顺序到达
                for (int j = 0; j < 5; j++) {
                    Message message = new Message(topic, "order_tag", ("Something to you the j[" + j + "] times in i[" + i + "].").getBytes(StandardCharsets.UTF_8));
                    // 参数2使用MessageQueueSelector，对应参数3可以传入一个和这个message相关的参数
                    // 这个参数最后会传递到内部类中select方法上的第三个参数 Object o
                    // 可以根据这个参数来选择发送到哪个队列里
                    // 这里用i作为参数，这样可以保证每个i相同的消息，都能到同一个队列
                    producer.send(message, new MessageQueueSelector() {
                        @Override
                        public MessageQueue select(List<MessageQueue> list, Message message, Object o) {
                            // 对于每个消息，根据传入的参数o决定使用list中的哪个队列
                            // 因为不能超出list的大小，所以使用取模
                            int index = (Integer) o;
                            index %= list.size();
                            return list.get(index);
                        }
                    }, i);
                    System.out.printf("消息 i[%d] j[%d] 发送成功\n", i, j);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            producer.shutdown();
        }
    }
}
