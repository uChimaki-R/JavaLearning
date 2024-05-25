package com.pockyr.polymorphism;

public class Student extends Person {
    public int sid;

    public Student() {
    }

    public Student(String name, int age, int sid) {
        super(name, age);
        this.sid = sid;
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Student");
    }

    public void doHomework() {
        System.out.println("doing homework...");
    }
}
