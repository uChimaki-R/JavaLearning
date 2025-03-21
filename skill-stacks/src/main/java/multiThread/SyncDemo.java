package multiThread;

class PrintThread implements Runnable {
    // 公共属性
    public int value;

    @Override
    public void run() {
        while (true) {
            synchronized (this) {
                // break条件
                if (value >= 100) break;
                String name = Thread.currentThread().getName();
                // 正确的线程遇到合适的数字才执行逻辑，虽然比较耗费cpu
                if (name.equals("odd") && value % 2 == 1) {
                    System.out.println(name + " increase value to " + ++value);
                } else if (name.equals("even") && value % 2 == 0) {
                    System.out.println(name + " increase value to " + ++value);
                }
            }
        }
    }
}

public class SyncDemo {
    public static void main(String[] args) {
        PrintThread pt = new PrintThread();
        Thread odd = new Thread(pt, "odd");
        Thread even = new Thread(pt, "even");
        odd.start();
        even.start();
    }
}
