package com.pockyr.annotation;

public class AnTest {
    // 本质是使用参数实例化了接口的类对象
    @MyAnnotation(value = "hello")
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
}
