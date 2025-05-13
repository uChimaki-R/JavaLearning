package handTearing.executor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: 手写线程池测试类
 * @Version: 1.0
 */
public class ExecutorTest {
    // 用main函数测试，@Test主逻辑跑完会直接结束
    public static void main(String[] args) {
        MyExecutor myExecutor = new MyExecutor(
                2,
                4,
                new ArrayBlockingQueue<>(2),
                1, TimeUnit.SECONDS,
                new ThrowRejectPolicy());

        // 7个任务刚好大于2+4+2，触发ThrowRejectPolicy()抛出异常（任务6没有执行）
        for (int i = 0; i < 7; i++) {
            final int finalI = i;
            myExecutor.execute(() -> {
                try {
                    // 模拟执行较慢
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " run task " + finalI + " finished");
            });
        }
        System.out.println("Main thread is not blocked");
    }
}
