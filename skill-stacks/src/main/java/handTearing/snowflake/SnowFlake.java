package handTearing.snowflake;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-15
 * @Description: 手写雪花算法
 * @Version: 1.0
 */
public class SnowFlake {
    /**
     * 指定开始时间，雪花算法可以支持到这个开始时间之后的69年（41位毫秒值表示的年数）
     * ((1L << 41) - 1) / (1000L * 60 * 60 * 24 * 365) = 69
     */
    public final long startStamp;
    /**
     * 机房号
     */
    public final long roomId;
    /**
     * 机器号
     */
    public final long machineId;
    /**
     * 上次的时间戳
     */
    public long lastStamp;
    /**
     * 当前序列号
     */
    public long sequence;

    public SnowFlake(long startStamp, long roomId, long machineId) {
        this.startStamp = startStamp;
        this.roomId = roomId;
        this.machineId = machineId;
        this.lastStamp = startStamp;
        this.sequence = 0L;
    }

    /**
     * 符号位1个bit
     */
    public final static long SYMBOL_BITS = 1;
    /**
     * 时间戳41个bit
     */
    public final static long STAMP_BITS = 41;
    /**
     * 机房号+机器号=10个bit
     */
    public final static long ROOM_BITS = 5;
    public final static long MACHINE_BITS = 5;
    /**
     * 序列号12个bit
     */
    public final static long SEQUENCE_BITS = 12;

    // 使用位运算，得出序列号的最大值
    public final static long SEQUENCE_MAX_VALUE = ~(-1L << SEQUENCE_BITS);

    /**
     * 获取下一个雪花id
     */
    public long getNextSnowflakeId() {
        long timestamp = System.currentTimeMillis();
        if (timestamp < lastStamp) {
            // 小于上一次的时间戳，发生时钟回拨
            // 解决方法：
            // 1. 直接抛错，让外部处理
            // 2. 等待时间再次重试，尝试让时钟走到LAST_STAMP之后，可以设定重试次数/重试超时时间
            // 3. 使用特殊的（机房+机器）标号，这个标号都来存发生时间回拨的数据，但是会和原来的数据不连续
            throw new RuntimeException("发生了时钟回拨问题，雪花id生成失败");
        }
        if (timestamp == lastStamp) {
            // 同一个时间戳，递增序列号
            sequence = (sequence + 1) & SEQUENCE_MAX_VALUE;
            if (sequence == 0) {
                // 达到最大值，溢出所以相与为0
                // 尝试获取下一毫秒
                long newTimestamp = System.currentTimeMillis();
                while (newTimestamp <= timestamp) {
                    newTimestamp = System.currentTimeMillis();
                }
                // 使用新的毫秒时间戳
                timestamp = newTimestamp;
            }
        } else {
            // 时间戳不同，序列号置零
//            sequence = 0L;
            // 这里置零有一个问题：在并发量不高的情况下bit尾数大概率为0，也就是雪花id大概率为偶数，如果分库分表是用id取模的话，会出现偶数表很大的情况
            // 解决方法，不使用0，而是使用时间戳的尾数，确保可能是0也可能是1
            sequence = timestamp & 1;
        }
        // 更新上一次时间戳
        lastStamp = timestamp;
        // 拼接雪花id
        return (timestamp - startStamp) << (SEQUENCE_BITS + MACHINE_BITS + ROOM_BITS)
                | roomId << (SEQUENCE_BITS + MACHINE_BITS)
                | machineId << (SEQUENCE_BITS)
                | sequence;
    }

    public static void main(String[] args) throws InterruptedException {
        long startStamp = System.currentTimeMillis();
        SnowFlake snowflake = new SnowFlake(startStamp, 10, 5);
        for (int i = 0; i < 100; i++) {
//            Thread.sleep(10);
            System.out.println(snowflake.getNextSnowflakeId());
        }
    }
}
