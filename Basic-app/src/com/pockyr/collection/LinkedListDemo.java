package com.pockyr.collection;

import java.util.LinkedList;

public class LinkedListDemo {
    public static void main(String[] args) {
        // 底层实现是双链表
        LinkedList<String> list = new LinkedList<>();
        // 设计队列
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");
        list.addLast("D");
        System.out.println(list);
        System.out.println(list.removeFirst());
        System.out.println(list.removeFirst());
        System.out.println(list.removeFirst());

        list.clear();
        // 设计栈
        list.push("G");  // 等价addFirst();
        list.push("H");
        list.push("I");
        list.push("J");
        System.out.println(list);
        System.out.println(list.pop());  // 等价removeFirst();
        System.out.println(list.pop());
        System.out.println(list.pop());
    }
}
