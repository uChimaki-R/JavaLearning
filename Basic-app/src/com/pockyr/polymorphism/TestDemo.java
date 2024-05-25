package com.pockyr.polymorphism;

public class TestDemo {
    public static void main(String[] args) {
        Student student = new Student("st", 7, 111);
        Teacher teacher = new Teacher("te", 50, 999);
        test(student);
        test(teacher);
    }
    public static void test(Person person) {
        person.sayHello();
    }
}
