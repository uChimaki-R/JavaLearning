package com.pockyr.stream;

import java.util.ArrayList;
import java.util.HashMap;

public class streamDemo {
    public static void main(String[] args) {
        ArrayList<String> list = new ArrayList<String>();
        list.add("Jack");
        list.add("Tom");
        list.add("Mary");
        list.add("Bob");
        list.stream().filter(e -> e.contains("o")).forEach(System.out::println);

        HashMap<String, Double> map = new HashMap<>();
        map.put("Jack", 1.0);
        map.put("Tom", 2.0);
        map.put("Mary", 3.0);
        map.put("Bob", 4.0);
        map.entrySet().stream().filter(e -> e.getValue() >= 3.0)
                .forEach(e -> System.out.println(e.getKey() + "->" + e.getValue()));
    }
}
