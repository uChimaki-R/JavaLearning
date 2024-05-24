package com.pockyr.javabean;

public class TestDemo {
    public static void main(String[] args) {
        Student student = new Student("zhang san", 90);
        StudentOperator operator = new StudentOperator(student);
        operator.judge();
    }
}
