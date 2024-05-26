package com.pockyr.interfacee;

import java.util.ArrayList;

public class ClassManager {
    ArrayList<Student> students = new ArrayList<>();
    // 使用不同的接口实现类可以获得不同的实现方式而无需修改后续的代码
    InfoOperatorIt ioi = new InfoOperatorMode2();

    public ClassManager() {
        students.add(new Student("Tom", 'b', 60));
        students.add(new Student("Amy", 'g', 93));
        students.add(new Student("Sam", 'b', 92));
        students.add(new Student("Mary", 'g', 77));
        students.add(new Student("Can", 'b', 34));
        students.add(new Student("Ton", 'b', 99));
    }

    public void printStudents() {
        ioi.printStudents(students);
    }

    public void getAverageScore() {
        ioi.getAverageScore(students);
    }
}
