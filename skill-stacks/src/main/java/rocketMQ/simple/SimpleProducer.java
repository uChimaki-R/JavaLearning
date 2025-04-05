package rocketMQ.simple;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class SimpleProducer {
    public static void main(String[] args) {
        DefaultMQProducer producer = new DefaultMQProducer("simple_producer");
        producer.setNamesrvAddr("192.168.202.135:9876");
        try {
            producer.start();
            System.out.println("Producer 启动成功");
            // 异步发送且需要回调时可以使用计数器等待消息全部发送完毕
            int total = 100;
            CountDownLatch countDownLatch = new CountDownLatch(total);
            // 发送信息
            for (int i = 0; i < total; i++) {
                Message message = new Message("topic-simple", "simple-tag", ("Hello world to you the [" + i + "] times!").getBytes(StandardCharsets.UTF_8));
//                producer.send(message); // send是同步方法，阻塞等待发送成功的响应，一般用于对消息一致性要求较高的场景
//                producer.sendOneway(message); // sendOneway是非阻塞方法，且没有回调，也就是不关心是否发送成功，一般用于日志发送
                // send使用两个参数就是异步发送回调，消息发送成功后回调onSuccess，失败回调onException
                producer.send(message, new SendCallback() {
                    @Override
                    public void onSuccess(SendResult sendResult) {
                        synchronized (countDownLatch) {
                            countDownLatch.countDown();
                            System.out.println("发送某个消息成功, 剩余未收到回复的消息数: " + countDownLatch.getCount());
                        }
                    }

                    @Override
                    public void onException(Throwable throwable) {
                        synchronized (countDownLatch) {
                            countDownLatch.countDown();
                            System.out.println("<!> 发送某个消息失败, 剩余未收到回复的消息数: " + countDownLatch.getCount());
                        }
                    }
                });
                System.out.println("发送消息: " + new String(message.getBody(), StandardCharsets.UTF_8));
            }
            // 等待消息全部发送成功
            boolean allSend = countDownLatch.await(5, TimeUnit.SECONDS);
            if (!allSend) {
                throw new Exception("5秒内没能收到所有消息的回复, 剩余未收到回复的消息数: " + countDownLatch.getCount());
            }
            System.out.println("所有消息都收到了回复!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            producer.shutdown();
        }
    }
}
