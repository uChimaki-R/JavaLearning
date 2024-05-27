package com.pockyr.arrays;

import java.util.Arrays;
import java.util.function.IntUnaryOperator;

public class ArraysDemo {
    public static void main(String[] args) {
        int[] array = {11, 44, 2, 33, 5};
        System.out.println(Arrays.toString(array));

        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

//  Arrays.setAll(array, new IntUnaryOperator() {
//      @Override
//      public int applyAsInt(int operand) {
//          return array[operand] + 1;
//      }
//  });
//
        // 可简化为lambda表达式
        Arrays.setAll(array, x -> array[x] + 1);
        System.out.println(Arrays.toString(array));
    }
}
