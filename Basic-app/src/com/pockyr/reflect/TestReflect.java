package com.pockyr.reflect;


import org.junit.Test;

public class TestReflect {
    @Test
    public void testGetClass() throws ClassNotFoundException {
        // 获取类对象
        // 通过类
        Class c = Cat.class;
        System.out.println(c.getName());  // com.pockyr.reflect.Cat
        System.out.println(c.getSimpleName());  // Cat
        // 通过类的实例
        Cat cat = new Cat("neko", 12);
        Class c2 = cat.getClass();
        System.out.println(c2.getName());
        System.out.println(c2.getSimpleName());
        // 通过Class的静态方法，需要传递全类名
        Class c3 = Class.forName("com.pockyr.reflect.Cat");
        System.out.println(c3.getName());
        System.out.println(c3.getSimpleName());
    }
}
