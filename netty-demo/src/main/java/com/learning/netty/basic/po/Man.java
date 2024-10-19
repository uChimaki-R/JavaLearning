package com.learning.netty.basic.po;

import com.learning.netty.basic.inter.Calculate;
import com.learning.netty.basic.inter.Communicate;

public class Man implements Communicate, Calculate {
    @Override
    public String sayHello(String name) {
        return "Hello " + name;
    }

    @Override
    public Integer calcAdd(Integer a, Integer b) {
        return a + b;
    }

    @Override
    public Integer calcDiv(Integer a, Integer b) {
        return a / b;
    }
}
