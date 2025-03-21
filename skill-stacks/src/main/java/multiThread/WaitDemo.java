package multiThread;

// 生产者和消费者和中间的“桌子”
class Inter {
    public static final Object lock = new Object();
    public static boolean isOddNow = false;
    public static final int total = 100;
    public static int value = 0;
}

class OddThread extends Thread {
    public OddThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            // 首先要持有锁
            synchronized (Inter.lock) {
                // break条件
                if (Inter.value >= Inter.total) break;
                if (Inter.isOddNow) {
                    // 是奇数，等待改为偶数
                    try {
                        Inter.lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // 是偶数，改为奇数并通知偶数线程来改
                    Inter.value++;
                    Inter.isOddNow = true;
                    System.out.println(getName() + " get value: " + Inter.value);
                    Inter.lock.notify();
                }
            }
        }
    }
}

class EvenThread extends Thread {
    public EvenThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        while (true) {
            synchronized (Inter.lock) {
                if (Inter.value >= Inter.total) break;
                if (Inter.isOddNow) {
                    Inter.value++;
                    Inter.isOddNow = false;
                    System.out.println(getName() + " get value: " + Inter.value);
                    Inter.lock.notify();
                } else {
                    try {
                        Inter.lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}

public class WaitDemo {
    public static void main(String[] args) {
        OddThread oddThread = new OddThread("odd");
        EvenThread evenThread = new EvenThread("even");
        oddThread.start();
        evenThread.start();
    }
}
