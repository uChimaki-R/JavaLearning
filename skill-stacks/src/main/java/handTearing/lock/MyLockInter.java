package handTearing.lock;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-05
 * @Description: 手写锁 接口
 * @Version: 1.0
 */
public interface MyLockInter {
    /**
     * 获取锁
     */
    void lock();

    /**
     * 释放锁
     */
    void unlock();
}
