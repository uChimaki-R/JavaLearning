package com.pockyr.abstractt;

public abstract class Person {
    public String name;
    public int age;
    public Person() {}
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
    // 使用final修饰防止修改模板
    public final void say(){
        System.out.println("-------------------------");
        System.out.println(this.name + " " + this.age);
        somethingYouSay();  // 模板中不同的部分使用抽象方法代替
    }
    // 要实例化的子类必须要重写抽象方法，也就一定会完善say函数
    public abstract void somethingYouSay();
}
