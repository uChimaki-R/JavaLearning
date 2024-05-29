package com.pockyr.thread;

public class TestDemo {
    public static void main(String[] args) {
        Thread t1 = new MyTread();
        t1.start();
        for (int i = 0; i < 10; i++) {
            System.out.println("主线程输出: " + i);
        }
    }
}
