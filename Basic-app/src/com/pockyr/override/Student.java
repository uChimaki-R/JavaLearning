package com.pockyr.override;

public class Student extends People {
    // 所有类都继承Object类
    private float score;

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Student() {
    }

    public Student(String name, int age, float score) {
        super(name, age);  // 使用super显式指定父类的构造方法
        this.score = score;
    }

    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }

    public int getAge() {
        return super.getAge();
    }

    public void setAge(int age) {
        super.setAge(age);
    }

    @Override  // 使用装饰器会自动检查 更加安全 可读性强
    public String toString() {
        return "Student [name=" + getName() + ", age=" + getAge() + "]";
    }
}
