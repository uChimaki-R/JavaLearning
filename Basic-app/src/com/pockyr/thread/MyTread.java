package com.pockyr.thread;

public class MyTread extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("子线程输出: " + i);
        }
    }
}
