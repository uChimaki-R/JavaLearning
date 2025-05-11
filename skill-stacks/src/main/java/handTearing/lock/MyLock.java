package handTearing.lock;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-05
 * @Description: 手写AQS锁
 * @Version: 1.0
 */
public class MyLock implements MyLockInter {
    /**
     * {@link MySimpleLock} 有以下几个问题：<br>
     * <br>
     * 1. 每个竞争锁的线程都需要不断的尝试持有锁，造成cpu大量负担<br>
     * 这个问题可以通过阻塞等待解决。阻塞等待是等持有锁的线程释放锁，但是很多线程都在等待，由此引出第二个问题：持有锁的线程该唤醒哪个线程（全唤醒会造成剧烈竞争）<br>
     * 解决方法是让竞争锁的线程排队等待，使用双向链表存储（pre的作用主要是删除节点（任务取消），这里没有体现），每次只唤醒头节点的下一个节点<br>
     * <br>
     * 2. 即使线程不持有锁，依旧可以调用unlock方法释放锁<br>
     * 这个问题可以通过添加一个线程属性标记当前持有锁的线程，非该线程调用unlock会进行报错，同时也解决了和lock一样的cpu负担问题（进入后续逻辑的只会是持有锁的那一个线程）<br>
     */

    private final AtomicBoolean isLocked = new AtomicBoolean(false);

    /**
     * 是否公平锁
     */
    private boolean isFair = false;

    public MyLock() {
    }

    public MyLock(boolean isFair) {
        this.isFair = isFair;
    }

    /**
     * 持有锁的线程
     */
    private Thread holdThread;

    /**
     * 双向链表头指针，并发下对引用的操作存在线程安全问题，所以需要使用原子类
     */
    private final AtomicReference<ThreadNode> head = new AtomicReference<>(new ThreadNode());

    /**
     * 双向链表尾指针，并发下对引用的操作存在线程安全问题，所以需要使用原子类（tail节点初始指向head节点）
     */
    private final AtomicReference<ThreadNode> tail = new AtomicReference<>(head.get());

    @Override
    public void lock() {
        // 公平锁不执行下面的if逻辑
        if (!isFair) {
            // 非公平锁，进来就会执行一次尝试获取锁的逻辑
            if (isLocked.compareAndSet(false, true)) {
                holdThread = Thread.currentThread();
                System.out.println(Thread.currentThread().getName() + " 插队获取到了锁");
                return;
            }
        }
        // 将自己加入到链表结尾
        ThreadNode thisNode = new ThreadNode();
        thisNode.thread = Thread.currentThread();
        // 由于涉及：获取tail节点、连接tail和自己节点、把自己设置为tail节点三个操作，在并发下存在线程安全问题
        // 但是将自己加入到尾节点这件事必须完成，所以需要while循环不断尝试直到成功
        while (true) {
            ThreadNode oldTail = tail.get();
            // 不能先执行连接tail和自己的操作，因为不能确定tail此时不会被别人修改，所以先执行把自己设置为tail的操作
            if (tail.compareAndSet(oldTail, thisNode)) {
                // 设置成功，别人获取的tail就会是自己了，这时候再来管理自己和原先的tail的连接操作
                oldTail.next = thisNode;
                thisNode.prev = oldTail;
                System.out.println(Thread.currentThread().getName() + " 将自己加入到了链表尾部");
                break;
            }
            // 否则一直执行
        }
        // 加入尾部后，先尝试获取一次锁（如果自己是第一个点，不应该等待，否则直接全部等待了），再进入等待操作
        while (true) {
            if (head.get().next == thisNode && isLocked.compareAndSet(false, true)) {
                // 自己是头节点的下一个并且获取到了锁
                // 这里只会有一个线程进入，所以没有线程安全问题
                holdThread = Thread.currentThread();
                // ！！注意！！不是把自己变到head.next，而是把自己变成head，这样unlock的时候唤醒head.next才是自己的next
                // 切除自己和前驱节点的联系
                thisNode.prev.next = null;
                thisNode.prev = null;
                // 将自己设置为头节点
                head.set(thisNode);
                System.out.println(Thread.currentThread().getName() + " 在链表头部获取到了锁");
                return;
            }
            // 否则进入等待
            LockSupport.park();
            // 存在虚假唤醒，但是外层是while，没有影响
        }
    }

    @Override
    public void unlock() {
        // 非持有锁的线程不可以执行unlock
        if (!Thread.currentThread().equals(holdThread)) {
            throw new RuntimeException(Thread.currentThread().getName() + " 未持有锁，无法执行解锁操作");
        }
        // 到这里一定是持有锁的线程，直接修改状态并唤醒head的下一个节点的线程
        isLocked.set(false);
        ThreadNode next = head.get().next;
        if (next != null) {
            System.out.println(Thread.currentThread().getName() + " 释放了锁，唤醒了 " + next.thread.getName());
            LockSupport.unpark(next.thread);
        } else {
            System.out.println(Thread.currentThread().getName() + " 释放了锁，但是当前链表已无后继节点，无需唤醒线程");
        }
    }

    /**
     * 双向链表节点
     */
    static class ThreadNode {
        ThreadNode prev;
        ThreadNode next;
        Thread thread;
    }
}
