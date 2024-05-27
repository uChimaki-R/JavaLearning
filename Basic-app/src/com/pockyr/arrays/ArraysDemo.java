package com.pockyr.arrays;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.function.IntUnaryOperator;

public class ArraysDemo {
    public static void main(String[] args) {
        int[] array = {11, 44, 2, 33, 5};
        System.out.println(Arrays.toString(array));

        Arrays.sort(array);
        System.out.println(Arrays.toString(array));

        // Arrays.setAll(array, new IntUnaryOperator() {
        //     @Override
        //     public int applyAsInt(int operand) {
        //         return array[operand] + 1;
        //     }
        // });

        // 可简化为lambda表达式
        Arrays.setAll(array, x -> array[x] + 1);
        System.out.println(Arrays.toString(array));

        Random rand = new Random();
        Student[] students = new Student[10];
        for (int i = 0; i < students.length; i++) {
            students[i] = new Student("学生"+i, 10+ rand.nextInt(10));
        }
        System.out.println(Arrays.toString(students));

        // 类中重写Comparable接口函数实现排序
        Arrays.sort(students);
        System.out.println(Arrays.toString(students));

        // sort传递重写的排序接口函数实现排序
        // Arrays.sort(students, new Comparator<Student>() {
        //     @Override
        //     public int compare(Student o1, Student o2) {
        //         return o2.getAge() - o1.getAge();
        //     }
        // });

        // 使用lambda表达式简化
        Arrays.sort(students, (x, y) -> y.getAge() - x.getAge());
        System.out.println(Arrays.toString(students));
    }
}
