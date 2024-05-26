package com.pockyr.interfacee;

import java.util.ArrayList;

public class InfoOperatorMode1 implements InfoOperatorIt {
    @Override
    public void printStudents(ArrayList<Student> students) {
        for (Student student : students) {
            System.out.println(">>>>>>>>>>>>>>");
            System.out.println(student);
            System.out.println("<<<<<<<<<<<<<<");
        }
    }

    @Override
    public void getAverageScore(ArrayList<Student> students) {
        double average = 0;
        for (Student student : students) {
            average += student.getScore();
        }
        average /= students.size();
        System.out.println("Average score: " + average);
    }
}
