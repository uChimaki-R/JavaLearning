package com.pockyr.interfacee;

import java.util.ArrayList;

public class InfoOperatorMode2 implements InfoOperatorIt {
    @Override
    public void printStudents(ArrayList<Student> students) {
        for (Student student : students) {
            System.out.println("~~~~~~~~~~~~~~");
            System.out.println(student);
            System.out.println("^^^^^^^^^^^^^^");
        }
    }

    @Override
    public void getAverageScore(ArrayList<Student> students) {
        double average = 0;
        double max = students.getFirst().getScore();
        double min = students.getFirst().getScore();
        for (Student student : students) {
            average += student.getScore();
        }
        average -= max;
        average -= min;
        average /= (students.size() - 2);
        System.out.println("Average score: " + average);
    }
}
