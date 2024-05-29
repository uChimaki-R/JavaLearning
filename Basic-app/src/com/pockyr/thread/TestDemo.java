package com.pockyr.thread;

import java.util.concurrent.*;

public class TestDemo {
    public static void main(String[] args) {
        try (
                /*
                 七个参数
                 int corePoolSize, // 核心线程数
                 int maximumPoolSize,  // 最大线程数，当核心线程都在忙且任务队列已满时，可以调用最多maximumPoolSize-corePoolSize个临时线程
                 long keepAliveTime,  // 临时线程的最大存活时间，和下一个参数组成完整的时间参数
                 TimeUnit unit,  // keepAliveTime参数的时间单位
                 BlockingQueue<Runnable> workQueue,  // 任务队列的存储方式，Array是固定长度，Linked链表是Integer.MAX_VALUE长度
                 ThreadFactory threadFactory,  // 线程工程，决定线程的创建方式，定义线程的名称等
                 RejectedExecutionHandler handler  // 过多任务的处理方式，Abort丢弃并报错，DiscardOldest替换队列里等了最久的任务，.Discard丢弃且不报错，CallerRuns交给主线程执行
                 */
                ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 5, 10, TimeUnit.SECONDS,
                        new ArrayBlockingQueue<>(5), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy())
        ) {
            threadPoolExecutor.execute(new MyTread());
            threadPoolExecutor.execute(new MyTread());
            threadPoolExecutor.execute(new MyTread());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
