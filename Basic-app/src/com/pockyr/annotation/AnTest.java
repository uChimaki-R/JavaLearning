package com.pockyr.annotation;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AnTest {
    // 本质是使用参数实例化了接口的类对象
    @MyAnnotation(value = "hello", id = 999)
    void test1() {
        System.out.println("test1");
    }

    // 只有一个没有默认值的话可以不指明参数名
    @MyAnnotation("hi")
    void test2() {
        System.out.println("test2");
    }

    void test3() {
        System.out.println("test3");
    }

    void test4() {
        System.out.println("test4");
    }

    public static void main(String[] args) {
        // 解析注解
        // 获取类对象
        AnTest t = new AnTest();
        Class c = AnTest.class;
        // 遍历类内的所有成员方法
        for (Method method : c.getDeclaredMethods()) {
            if (method.isAnnotationPresent(MyAnnotation.class)) {
                // 如果方法有MyAnnotation的注解
                try {
                    // 执行该方法
                    method.invoke(t);
//                    test1
//                    test2
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
