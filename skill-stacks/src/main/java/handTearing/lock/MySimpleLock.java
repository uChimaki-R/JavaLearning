package handTearing.lock;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-05
 * @Description: 手写锁 最简单实现
 * @Version: 1.0
 */
public class MySimpleLock implements MyLockInter {

    /**
     * 标记锁是否已经被持有，使用原子类
     */
    private final AtomicBoolean isLocked = new AtomicBoolean(false);

    @Override
    public void lock() {
        while (true) {
            if (isLocked.compareAndSet(false, true)) {
                return;
            }
        }
    }

    @Override
    public void unlock() {
        while (true) {
            if (isLocked.compareAndSet(true, false)) {
                return;
            }
        }
    }
}
