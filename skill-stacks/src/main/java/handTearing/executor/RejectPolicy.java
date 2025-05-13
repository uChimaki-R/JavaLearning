package handTearing.executor;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: 任务拒绝策略
 * @Version: 1.0
 */
public interface RejectPolicy {
    void reject(MyExecutor executor, Runnable command);
}
