package com.learning.netty.eventloop;

import com.learning.netty.basic.inter.Communicate;

import java.util.ServiceLoader;

public class TestSomething {
    public static void main(String[] args) {
        ServiceLoader<Communicate> load = ServiceLoader.load(Communicate.class);
        for (Communicate c : load) {
            System.out.println(c.getClass());
        }
    }
}
