package handTearing.rateLimited;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-18
 * @Description: 手写令牌桶算法
 * @Version: 1.0
 */
public class TokenBucket {
    private final double capacity;
    private final double tokensPerSecond;

    private double currentTokens;
    private long lastGenerateTime;

    public TokenBucket(double capacity, double tokensPerSecond) {
        assert capacity > 0;
        assert tokensPerSecond > 0;
        this.capacity = capacity;
        this.tokensPerSecond = tokensPerSecond;
        // 初始容量满
        this.currentTokens = capacity;
        // 上次生成时间就是创建时间
        this.lastGenerateTime = System.currentTimeMillis();
    }

    public boolean tryAcquire() {
        return tryAcquire(1);
    }

    public synchronized boolean tryAcquire(double permits) {
        assert permits > 0;
        // 先生成令牌（类似懒加载）
        generateTokens();
        if (permits > currentTokens) {
            return false;
        }
        currentTokens -= permits;
        return true;
    }

    private void generateTokens() {
        long current = System.currentTimeMillis();
        if (current < lastGenerateTime) {
            // 时钟回拨，直接更新时间，没得生成
            lastGenerateTime = current;
            return;
        }
        // 计算生成数量
        double generateCounts = (current - lastGenerateTime) / 1000.0 * tokensPerSecond;
        // 更新令牌数
        currentTokens = Math.min(capacity, currentTokens + generateCounts);
        // 更新更新时间
        lastGenerateTime = current;
    }

    public static void main(String[] args) throws InterruptedException {
        // 创建一个容量为5，每秒补充2个令牌的桶
        TokenBucket bucket = new TokenBucket(5, 2);

        // 初始状态应该允许5次获取
        System.out.println("初始化5个令牌");
        for (int i = 1; i <= 5; i++) {
            System.out.println("请求1-" + i + ": " + bucket.tryAcquire());
        }

        // 第6次获取应该失败
        System.out.println("请求1-6: " + bucket.tryAcquire());

        // 等待1秒后补充2个令牌
        System.out.println("等待1秒，期间会补充2个令牌");
        Thread.sleep(1000);
        for (int i = 0; i < 3; i++) {
            // 前两次成功，第三次失败
            System.out.println("请求2-" + i + ": " + bucket.tryAcquire());
        }
    }
}
