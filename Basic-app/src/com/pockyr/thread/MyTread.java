package com.pockyr.thread;

import java.util.concurrent.Callable;

// Callable接口的泛型指代返回值类型
public class MyTread implements Callable<String> {
    // 执行任务中所需的参数在实例化对象的时候传递
    private final int n;

    public MyTread(int n) {
        this.n = n;
    }

    @Override
    public String call() {
        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += i;
        }
        return "result: " + sum;
    }
}
