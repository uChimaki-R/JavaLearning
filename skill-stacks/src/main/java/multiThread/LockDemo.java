package multiThread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class LockThread implements Runnable {
    public Lock lock = new ReentrantLock();
    public String lastOprName = "even";
    public int value = 0;
    public int total;

    public LockThread(int total) {
        this.total = total;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                // break条件
                if (value >= total) break;
                // 名字不是自己就改，并更新名字
                if (!lastOprName.equals(Thread.currentThread().getName())) {
                    System.out.println(Thread.currentThread().getName() + " get " + ++value);
                    lastOprName = Thread.currentThread().getName();
                }
            } finally {
                lock.unlock();
            }
        }
    }
}

public class LockDemo {
    public static void main(String[] args) {
        LockThread lockThread = new LockThread(100);
        Thread even = new Thread(lockThread, "even");
        Thread odd = new Thread(lockThread, "odd");
        even.start();
        odd.start();
    }
}
