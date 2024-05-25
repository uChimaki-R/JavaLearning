package com.pockyr.override;

public class Student {
    // 所有类都继承Object类
    private String name;
    private int age;

    public Student() {
    }

    public Student(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override  // 使用装饰器会自动检查 更加安全 可读性强
    public String toString() {
        return "Student [name=" + name + ", age=" + age + "]";
    }
}
