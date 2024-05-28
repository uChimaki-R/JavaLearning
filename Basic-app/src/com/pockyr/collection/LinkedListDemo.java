package com.pockyr.collection;

import java.util.LinkedList;

public class LinkedListDemo {
    public static void main(String[] args) {
        LinkedList<String> list = new LinkedList<String>();
        // 设计队列
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");
        System.out.println(list);
        System.out.println(list.removeFirst());
        System.out.println(list.removeFirst());
        System.out.println(list.removeFirst());
    }
}
