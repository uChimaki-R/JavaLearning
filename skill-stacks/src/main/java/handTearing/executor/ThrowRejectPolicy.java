package handTearing.executor;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: 抛出异常的拒绝策略
 * @Version: 1.0
 */
public class ThrowRejectPolicy implements RejectPolicy {
    @Override
    public void reject(MyExecutor executor, Runnable command) {
        throw new RuntimeException("Rejected");
    }
}
