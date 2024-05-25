package com.pockyr.override;

public class Student extends People {
    // 所有类都继承Object类
    private float score;

    public Student() {
    }

    public Student(String name, int age) {
        // java没有默认参数，使用this指定兄弟(自己)的构造方法，传递默认参数
        // 如果直接重载构造函数会有很多重复性代码
        this(name, age, 60);
    }

    public Student(String name, int age, float score) {
        super(name, age);  // 使用super显式指定父类的构造方法
        this.score = score;
    }


    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Student{" +
                "score=" + score + '\t' +
                super.toString() +
                '}';
    }
}
