package multiThread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class AddThread extends Thread {
    // 每个线程都有两个队列属性，但是实际上是公共属性，main里用两个队列
    // 但是因为两个线程的队列是交换的（一个的from是另一个的to），所以不能改为static
    public BlockingQueue<Integer> from;
    public BlockingQueue<Integer> to;
    public int total;

    public AddThread(BlockingQueue<Integer> from, BlockingQueue<Integer> to, int total, String name) {
        super(name);
        this.from = from;
        this.to = to;
        this.total = total;
    }

    public void run() {
        while (true) {
            try {
                // 从from中取数字
                Integer take = from.take();
                System.out.println(getName() + " takes " + take);
                // 拿到的是最后一个，自己退出
                if (take >= total) break;
                // ++后放到to
                take++;
                to.put(take);
                // 送进去的是最后一个，自己退出
                if (take >= total) break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

public class BlockingQueueDemo {
    public static void main(String[] args) {
        BlockingQueue<Integer> from = new ArrayBlockingQueue<Integer>(1);
        BlockingQueue<Integer> to = new ArrayBlockingQueue<Integer>(1);
        // 单用一个队列，可能偶数把数字放进去后奇数还没来得及取出打印，偶数就把下一个数字打印了，所以还是两个队列互相取一个数字好
        int total = 100;

        AddThread odd = new AddThread(from, to, total, "odd");
        // from to 互换
        AddThread even = new AddThread(to, from, total, "even");

        // 初始化放入1，从odd线程开始
        from.add(1);

        odd.start();
        even.start();
    }
}
