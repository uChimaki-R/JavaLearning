package multiThread;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicDemo {
    // 用atomic对象作为共享属性
    public static AtomicInteger atomicInteger = new AtomicInteger();
    public static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while (true) {
                int value = atomicInteger.get();
                if (value >= 100) break;
                if (flag) {
                    System.out.println(Thread.currentThread().getName() + " gets " + atomicInteger.incrementAndGet());
                    flag = false;
                }
            }
        }, "odd").start();

        new Thread(() -> {
            while (true) {
                int value = atomicInteger.get();
                if (value >= 100) break;
                if (!flag) {
                    System.out.println(Thread.currentThread().getName() + " gets " + atomicInteger.incrementAndGet());
                    flag = true;
                }
            }
        }, "even").start();
    }
}
