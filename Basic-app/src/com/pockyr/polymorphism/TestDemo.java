package com.pockyr.polymorphism;

public class TestDemo {
    public static void main(String[] args) {
        Student student = new Student("st", 7, 111);
        Teacher teacher = new Teacher("te", 50, 999);
        test(teacher);
        test(student);
    }

    public static void test(Person person) {
        person.sayHello();
        if (person instanceof Student student) {
            student.doHomework();
        } else if (person instanceof Teacher teacher) {
            teacher.setHomework();
        }
    }
}
