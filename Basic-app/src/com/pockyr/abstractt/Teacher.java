package com.pockyr.abstractt;

public class Teacher extends Person {
    public int tid;

    public Teacher() {

    }

    public Teacher(String name, int age, int tid) {
        super(name, age);
        this.tid = tid;
    }

    @Override
    public void somethingYouSay() {
        System.out.println("Teacher saying something...");
        System.out.println("Oh, my tid is " + tid);
    }
}
