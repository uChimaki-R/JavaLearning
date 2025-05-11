package handTearing.rateLimited;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-18
 * @Description: 手写漏桶算法
 * @Version: 1.0
 */
public class LeakyBucket {
    private final double capacity;
    private final double leakPerSecond;

    private double currentWater;
    private long lastLeakTime;

    public LeakyBucket(double capacity, double leakPerSecond) {
        assert capacity > 0;
        assert leakPerSecond > 0;
        this.capacity = capacity;
        this.leakPerSecond = leakPerSecond;
        // 初始化为空桶
        currentWater = 0;
        // 上次漏水时间为创建时间
        lastLeakTime = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        return tryAcquire(1);
    }

    public synchronized boolean tryAcquire(double permits) {
        assert permits > 0;
        // 先漏水给出容积（类似懒加载）
        leakWater();
        if (permits > capacity - currentWater) {
            // 剩余容量无法加入
            return false;
        }
        currentWater += permits;
        return true;
    }

    private void leakWater() {
        long current = System.currentTimeMillis();
        if (current < lastLeakTime) {
            // 时钟回拨，无法漏水，修正时钟回拨，返回
            lastLeakTime = current;
            return;
        }
        // 计算漏水大小
        double total = (current - lastLeakTime) / 1000.0 * leakPerSecond;
        // 不能小于0
        currentWater = Math.max(currentWater - total, 0);
        // 更新漏水时间
        lastLeakTime = current;
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建一个容量为10，每秒漏1个的桶
        LeakyBucket bucket = new LeakyBucket(10, 1);

        // 初始允许10次请求
        System.out.println("初始没有水，可以容纳10的permit");
        for (int i = 1; i <= 10; i++) {
            System.out.println("请求1-" + i + ": " + bucket.tryAcquire(1));
        }

        // 第11个请求失败
        System.out.println("请求1-11: " + bucket.tryAcquire(1));

        // 等待1秒后漏出1个
        System.out.println("等待1秒后，获取到1的可用容量");
        Thread.sleep(1000);
        // 可以获取
        System.out.println("请求2-1: " + bucket.tryAcquire(1));
        // 获取失败
        System.out.println("请求2-2: " + bucket.tryAcquire(1));

        // 再等待2秒后漏出2个
        System.out.println("再等待2秒，获取到2的可用容量");
        Thread.sleep(2000);
        // 获取8的容量，失败
        System.out.println("请求3-1（try 8）: " + bucket.tryAcquire(8));
        // 获取2的容量，成功
        System.out.println("请求3-2（try 2）: " + bucket.tryAcquire(2));
        // 获取1的容量，失败
        System.out.println("请求3-3（try 1）: " + bucket.tryAcquire(1));
    }
}
