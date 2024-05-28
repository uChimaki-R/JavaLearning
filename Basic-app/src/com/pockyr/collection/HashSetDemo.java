package com.pockyr.collection;

import java.util.HashSet;

public class HashSetDemo {
    public static void main(String[] args) {
        // 底层实现是数组+链表+红黑树(链表长度过长时)
        HashSet<Student> hashSet = new HashSet<>();
        hashSet.add(new Student("Tom", 13));
        hashSet.add(new Student("Jack", 15));
        // new出来的Student的成员变量即使相同，hash出来的索引大概率不同
        // 需要重写hashCode()和equals()方法解决去重问题
        hashSet.add(new Student("Mary", 16));
        hashSet.add(new Student("Mary", 16));
        System.out.println(hashSet);
    }
}
