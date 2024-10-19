package com.learning.netty.basic.po;

import com.learning.netty.basic.inter.Communicate;

public class Speaker implements Communicate {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello " + name);
    }
}
