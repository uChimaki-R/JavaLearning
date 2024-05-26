package com.pockyr.generic;

import java.util.ArrayList;

public class TestDemo {
    public static void main(String[] args) {
        test("111");
        ArrayList<BENZ> bs = new ArrayList<>();
        bs.add(new BENZ());
        bs.add(new BENZ());
        bs.add(new BENZ());
        ArrayList<BMW> bmws = new ArrayList<>();
        bmws.add(new BMW());
        bmws.add(new BMW());
        bmws.add(new BMW());
        print(bs);
        print(bmws);
    }
    // 泛型方法
    public static <T> void test(T t) {
        System.out.println(t);
    }
    // 参数是泛型可以不用自己再写一遍泛型，而是使用通配符 ?
    // 使用extends要求泛型需要是其子类(上限) super指定需要是其父类(下限)
    public static void print(ArrayList<? extends Car> arrayList){
        for (Object o : arrayList) {
            System.out.println(o);
        }
    }
}
