package com.pockyr.override;

public class TestDemo {
    public static void main(String[] args) {
        Student s = new Student("张三", 12, 90);
        System.out.println(s);
        Student s2 = new Student("李四", 23);
        System.out.println(s2);
    }
}
