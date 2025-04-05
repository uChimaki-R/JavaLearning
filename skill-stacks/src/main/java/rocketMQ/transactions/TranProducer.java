package rocketMQ.transactions;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

// 发送事务消息会先发送半消息到一个事务topic中（默认有的一个topic），事务提交后才会到指定的topic中给消费者消费
// 发送消息后broker会回调这里的executeLocalTransaction方法，两种方法都会返回事务当前状态，一共三种：无状态、提交、回滚
// 如果返回给broker提交，则消息正常到指定topic，如果返回回滚，消息会被broker丢弃
// 如果返回无状态，broker会间断的调用checkLocalTransaction请求再次返回状态，超过15次后不再调用，消息会进入假提交状态，保存到broker中
class MyTransactionListener implements TransactionListener {
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object o) {
        return null;
    }

    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        return null;
    }
}

public class TranProducer {
    public static void main(String[] args) {
        TransactionMQProducer producer = getTransactionMQProducer();
        try {
            producer.start();
            for (int i = 0; i < 10; i++) {
                Message msg = new Message("trans_topic", "trans_tag", ("Hello World" + i).getBytes());
                // send使用带Transaction的
                // 第二个参数类似顺序消息传给内部类选择队列的参数，这里传入给事务listener，可以传入事务id等上下文数据
                producer.sendMessageInTransaction(msg, null);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            producer.shutdown();
        }
    }

    private static TransactionMQProducer getTransactionMQProducer() {
        // 发送事务消息需要用不同的Producer，这个继承了DefaultMQProducer，原来的方法还是可以用的
        TransactionMQProducer producer = new TransactionMQProducer("tran_producer");
        producer.setNamesrvAddr("192.168.202.135:9876");
        // 添加事务监听器
        producer.setTransactionListener(new MyTransactionListener());
        // 配置线程池用于执行事务检查
        ExecutorService executorService = new ThreadPoolExecutor(
            2,
            5,
            100,
            TimeUnit.SECONDS,
            new ArrayBlockingQueue<>(2000),
            r -> {
                Thread thread = new Thread(r);
                thread.setName("transaction-check-thread");
                return thread;
            });
        producer.setExecutorService(executorService);
        return producer;
    }
}
