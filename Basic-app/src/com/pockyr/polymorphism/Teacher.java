package com.pockyr.polymorphism;

public class Teacher extends Person {
    public int tid;

    public Teacher(String name, int age, int tid) {
        super(name, age);
        this.tid = tid;
    }

    @Override
    public void sayHello() {
        System.out.println("Hello, I am Teacher");
    }

    public void setHomework() {
        System.out.println("setting homework...");
    }
}
