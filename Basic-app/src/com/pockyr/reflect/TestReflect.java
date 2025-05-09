package com.pockyr.reflect;


import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class TestReflect {
    @Test
    public void testGetClass() throws ClassNotFoundException {
        // 获取类对象
        // 通过类
        Class c = Cat.class;
        System.out.println(c.getName());  // com.pockyr.reflect.Cat
        System.out.println(c.getSimpleName());  // Cat
        // 通过类的实例
//        Cat cat = new Cat("neko", 12);
//        Class c2 = cat.getClass();
//        System.out.println(c2.getName());
//        System.out.println(c2.getSimpleName());
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
        // 使用 getDeclaredConstructors 获取全部构造器，后续同理
        Constructor[] cs2 = c.getDeclaredConstructors();
        for (Constructor constructor : cs2) {
            System.out.println(constructor.getName() + " --> parameter counts: " + constructor.getParameterCount());
        }

        System.out.println("--------------------------------------------");
        // 获取指定的构造器
        Constructor constructor = c.getDeclaredConstructor(String.class, int.class);
        System.out.println(constructor.getName() + " --> parameter counts: " + constructor.getParameterCount());

        // 使用构造器初始化对象
        try {
            // 此构造函数在类内是private修饰的，可以通过setAccessible取消检查，暴力反射，会破坏类的封装性
            constructor.setAccessible(true);

            Cat cat = (Cat) constructor.newInstance("neko", 12);
            System.out.println(cat.getName());
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testGetField() throws NoSuchFieldException {
        // 获取类的成员变量
        Class c = Cat.class;
        Field[] fs = c.getDeclaredFields();
        for (Field f : fs) {
            System.out.println(f.getName() + ": " + f.getType());
        }
        // 用于实例对象的取值和赋值
        Cat cat = new Cat("neko", 12);
        Field field = c.getDeclaredField("name");
        field.setAccessible(true);
        try {
            field.set(cat, "ShiLo");  // 设置私有的变量信息
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cat.getName());
    }

    @Test
    public void testGetMethod(){
        // 获取类的成员方法
        Class c = Cat.class;
        Method[] ms = c.getDeclaredMethods();
        for (Method m : ms) {
            System.out.println(m.getName() + ": " + Arrays.toString(m.getParameters()) + " --> " + m.getReturnType());
        }
        // 获取指定的成员方法
        try {
            Method method = c.getDeclaredMethod("saySomething", String.class, int.class);
            System.out.println(method);
            // 使用反射调用执行方法
            method.setAccessible(true);
            Cat cat = new Cat("ShiLo", 12);
            try {
                method.invoke(cat, "nia~", 5);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
