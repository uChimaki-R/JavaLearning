package handTearing.executor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: 手写线程池
 * @Version: 1.0
 */
public class MyExecutor {
    public MyExecutor(int coreThreadCount, int maxThreadCount, BlockingQueue<Runnable> taskQueue, int timeout, TimeUnit timeoutUnit, RejectPolicy rejectPolicy) {
        this.coreThreadCount = coreThreadCount;
        this.maxThreadCount = maxThreadCount;
        this.taskQueue = taskQueue;
        this.timeout = timeout;
        this.timeoutUnit = timeoutUnit;
        this.rejectPolicy = rejectPolicy;
    }

    /**
     * 核心线程数
     */
    private final int coreThreadCount;
    /**
     * 总线程数（核心线程+辅助线程）
     */
    private final int maxThreadCount;
    /**
     * 任务队列（阻塞队列）
     */
    private final BlockingQueue<Runnable> taskQueue;
    /**
     * 辅助线程超时时间
     */
    private final int timeout;
    /**
     * 辅助线程超时时间单位
     */
    private final TimeUnit timeoutUnit;
    /**
     * 任务拒绝策略
     */
    private final RejectPolicy rejectPolicy;

    /**
     * 线程列表，分为核心线程数组和辅助线程数组，这样辅助线程remove也快一点（前面遍历的都是核心线程）
     */
    private final List<Thread> coreThreads = new ArrayList<Thread>();
    private final List<Thread> supportThreads = new ArrayList<Thread>();

    /**
     * 执行逻辑：<br>
     * 1. 核心线程数是否已满，已满则放入任务队列，未满则创建核心线程执行逻辑<br>
     * 2. 任务队列是否已满，已满时是否能够构造辅助线程来执行逻辑，能则构建<br>
     * 3. 全部线程都已忙碌，提交任务失败，触发拒绝政策<br>
     * 所以对外要求初始化 {@link MyExecutor#coreThreadCount}, {@link MyExecutor#maxThreadCount}, {@link MyExecutor#rejectPolicy}<br>
     * <br>
     * 如果使用 new Thread(command).start() 这样构造线程，则在执行完command逻辑后，即使有将该线程保存，该线程也无法复用（生命周期结束）<br>
     * 所以线程执行的逻辑不应该是传入的command逻辑，而是不断获取添加的command并执行，是一个while循环<br>
     * 但是使用List，用while(true)+if(!list.isEmpty())会造成cpu大量的空转，所以应该使用阻塞队列来提交任务和获取任务<br>
     * 阻塞队列有一个容量参数，同时也有不同的实现类型，所以直接全部让外部初始化 {@link MyExecutor#taskQueue}<br>
     * 辅助线程在任务较少的时候应该自行结束，对于多久没有执行到任务的阈值设定也要让外部指定，所以需要初始化超时时间 {@link MyExecutor#timeout} 和单位 {@link MyExecutor#timeoutUnit}
     */
    public void execute(Runnable command) {
        // 下面的判断基本都有线程安全问题，但是这里没有解决
        if (coreThreads.size() < coreThreadCount) {
            // 创建核心线程，执行不断从任务队列中获取任务并执行的逻辑
            Thread coreThread = new CoreThread();
            coreThreads.add(coreThread);
            coreThread.start();
        }
        // 因为核心线程会从人物队列中获取任务，这里直接把任务存到队列中即可
        // 核心线程创建完成后，不会执行创建核心线程的逻辑，但是还是会把任务存入队列中，正好符合逻辑（虽然有点倒果为因的感觉）
        boolean isSuccess = taskQueue.offer(command);
        if (isSuccess) {
            // 存入任务成功，直接返回
            return;
        }
        // 存入失败，尝试构造辅助线程执行该任务
        if (coreThreads.size() + supportThreads.size() < maxThreadCount) {
            // 创建辅助线程，执行该任务后再和核心线程一样执行获取任务并执行的逻辑
            Thread supportThread = new SupportThread(command);
            supportThreads.add(supportThread);
            supportThread.start();
            return;
        }
        // 到这里说明核心线程和辅助线程都用完了，再次尝试offer任务
        isSuccess = taskQueue.offer(command);
        if (!isSuccess) {
            // offer失败，需要执行拒绝策略
            rejectPolicy.reject(this, command);
        }
    }

    private class CoreThread extends Thread {
        CoreThread() {
            super();
            System.out.println("CoreThread created");
        }

        @Override
        public void run() {
            // 不断从任务队列中获取任务并执行
            while (true) {
                try {
                    Runnable task = taskQueue.take();
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    private class SupportThread extends Thread {
        private final Runnable firstTask;

        SupportThread(Runnable task) {
            super();
            // 辅助线程需要先把传入的任务（无法存入任务队列的任务）执行完毕后，才能和核心线程一样不断从任务队列中取任务
            firstTask = task;
            System.out.println("SupportThread created");
        }

        @Override
        public void run() {
            // 执行传入的任务
            firstTask.run();
            // 不断从任务队列中取任务执行
            while (true) {
                try {
                    // 取任务的时候和核心线程不一样，有个超时时间，超过这个时间就把线程结束了
                    // 所以使用带超时时间的poll
                    Runnable task = taskQueue.poll(timeout, timeoutUnit);
                    if (task == null) {
                        break;
                    }
                    task.run();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            // 结束把自己移除
            supportThreads.remove(Thread.currentThread());
            System.out.println("SupportThread removed");
        }
    }
}
