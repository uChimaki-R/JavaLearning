package com.pockyr.thread;

import java.util.concurrent.Callable;

// Callable接口的泛型指代返回值类型
public class MyTread implements Runnable {
    public MyTread() {
    }

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + "输出: " + i);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
