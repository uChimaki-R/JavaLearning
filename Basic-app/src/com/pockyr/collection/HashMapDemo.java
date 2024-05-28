package com.pockyr.collection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<String, Student> hashMap = new HashMap<>();
        hashMap.put("Tom", new Student("Tom", 13));
        hashMap.put("Jack", new Student("Jack", 15));
        hashMap.put("Jane", new Student("Jane", 17));
        hashMap.put("Bob", new Student("Bob", 19));
        hashMap.put("John", new Student("John", 20));
        System.out.println(hashMap);

        // 遍历方式
        // 使用entry集合
        Set<Map.Entry<String, Student>> entries = hashMap.entrySet();
        for (Map.Entry<String, Student> entry : entries) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // 使用lambda表达式
        hashMap.forEach((k, v) -> System.out.println(k + "--->" + v));
    }
}
