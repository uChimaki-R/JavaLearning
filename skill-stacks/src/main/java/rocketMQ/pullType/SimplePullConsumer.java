package rocketMQ.pullType;

import org.apache.rocketmq.client.consumer.DefaultLitePullConsumer;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import rocketMQ.utils.SimpleSend;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SimplePullConsumer {
    public static void main(String[] args) throws Exception {
//        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer();
        // 原生的DefaultMQPullConsumer需要自己遍历topic、遍历队列获取数据，判断获取状态，还要维护position变量，非常繁琐，参考https://www.bilibili.com/video/BV1ScimY5EQ6 P10
        // 使用Lite实现，随机获取自己监听的topic中的一个队列
        DefaultLitePullConsumer consumer = new DefaultLitePullConsumer("pull_consumers");
        consumer.setNamesrvAddr("192.168.202.135:9876");
        consumer.start();

        // 有两种方式：随机获取一个消息队列、指定获取一个消息队列
        String topic = "pull-topic";
        SimpleSend.send(topic, "simple-tag", "Pull Test", 100);
        getQueueByRandom(consumer, topic);
//        getQueueBySeek(consumer, topic);
    }

    public static void getQueueByRandom(DefaultLitePullConsumer consumer, String topic) {
        try {
            consumer.subscribe(topic);
            int count = 0;
            while (true) {
                System.out.println("========第" + ++count + "次获取消息队列========");
                // 随机获取一个队列
                List<MessageExt> messageExtList = consumer.poll();
                if (messageExtList.isEmpty()) {
                    break;
                }
                messageExtList.forEach(message -> {
                    System.out.println("获取到消息: " + message);
                });
            }
            System.out.println("队列暂无消息，选择关闭消费者");
            consumer.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void getQueueBySeek(DefaultLitePullConsumer consumer, String topic) {
        try {
            // 获取主题的所有消息队列
            Collection<MessageQueue> allMsgQueues = consumer.fetchMessageQueues(topic);
            // 指定从哪个队列中获取（传入列表，这里传入其中一个）
            MessageQueue chosenQueue = allMsgQueues.iterator().next();
            System.out.println("选择了队列: " + chosenQueue);
            consumer.assign(Collections.singletonList(chosenQueue));
            int count = 1;
            while (true) {
                System.out.println("========第" + count++ + "次获取消息队列========");
                // 这里获取的就是上面seek指定的队列（参数1）
                List<MessageExt> messageExtList = consumer.poll();
                if (messageExtList.isEmpty()) {
                    break;
                }
                messageExtList.forEach(message -> {
                    System.out.println("获取到消息: " + message);
                });
            }
            System.out.println("队列暂无消息，选择关闭消费者");
            consumer.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
