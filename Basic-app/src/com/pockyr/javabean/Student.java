package com.pockyr.javabean;

// 实体类 成员变量都私有 都拥有set get方法 拥有无参构造
// 只是数据的承载实体，对数据的具体处理操作方法由另一个类来完成，实现数据与数据业务处理的分离

public class Student {
    private String name;
    private int age;

    public Student() {}

    // 可以有有参构造，但是写了有参构造需要显式写出无参构造
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
}
