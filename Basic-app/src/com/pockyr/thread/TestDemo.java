package com.pockyr.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class TestDemo {
    public static void main(String[] args) {
        // 创建一个实现了Callable接口的类实例
        Callable<String> callable = new MyTread(100);
        // 使用FutureTask包装该类实例，使之成为一个任务对象
        FutureTask<String> futureTask = new FutureTask<>(callable);
        // 将该任务对象传递到Thread的初始化参数中
        Thread t1 = new Thread(futureTask);
        // 启动该任务
        t1.start();
        // 使用FutureTask.get方法获取子进程执行结果
        try {
            String result = futureTask.get();
            System.out.println(result);
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }
}
