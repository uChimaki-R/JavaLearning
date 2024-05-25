package com.pockyr.abstractt;

public class TestDemo {
    public static void main(String[] args) {
        Student student = new Student("st", 7, 111);
        Teacher teacher = new Teacher("te", 50, 999);
        test(teacher);
        test(student);
    }

    public static void test(Person person) {
        person.say();
    }
}
