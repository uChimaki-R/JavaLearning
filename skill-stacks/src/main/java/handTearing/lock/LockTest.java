package handTearing.lock;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-04
 * @Description: 手写锁测试类
 * @Version: 1.0
 */
public class LockTest {
    public static void main(String[] args) throws InterruptedException {
        int[] count = new int[]{1000};
        Thread[] threads = new Thread[10];
//        MyLockInter lock = new MyLock();
        MyLockInter lock = new MyLock(true);
        for (int i = 0; i < 10; i++) {
            threads[i] = new Thread(() -> {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.lock();
                for (int i1 = 0; i1 < 100; i1++) {
                    count[0]--;
                }
                lock.unlock();
            });
            threads[i].start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(count[0]);
    }
}