package com.pockyr.abstractt;

public class Student extends Person {
    public int sid;

    public Student() {
    }

    public Student(String name, int age, int sid) {
        super(name, age);
        this.sid = sid;
    }

    @Override
    public void somethingYouSay() {
        System.out.println("Student saying something...");
        System.out.println("Oh, my sid is " + this.sid);
    }

}
