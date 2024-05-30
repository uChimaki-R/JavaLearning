package com.pockyr.reflect;


import org.junit.Test;

import java.lang.reflect.Constructor;

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

    @Test
    public void testGetConstructor() throws NoSuchMethodException {
        Class c = Cat.class;
        // 只能获取public的构造器
        Constructor[] cs = c.getConstructors();
        for (Constructor constructor : cs) {
            System.out.println(constructor.getName() + " --> parameter counts: " + constructor.getParameterCount());
        }
        System.out.println("--------------------------------------------");
        // 使用 getDeclaredConstructors 获取全部构造器，下面的 getDeclaredConstructor 同理
        Constructor[] cs2 = c.getDeclaredConstructors();
        for (Constructor constructor : cs2) {
            System.out.println(constructor.getName() + " --> parameter counts: " + constructor.getParameterCount());
        }

        System.out.println("--------------------------------------------");
        // 获取指定的构造器
        Constructor constructor = c.getDeclaredConstructor(String.class, int.class);
        System.out.println(constructor.getName() + " --> parameter counts: " + constructor.getParameterCount());
    }
}
