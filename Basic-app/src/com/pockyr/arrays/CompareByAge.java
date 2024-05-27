package com.pockyr.arrays;

public class CompareByAge {
    public static int compareByAge(Student s1, Student s2) {
        return Integer.compare(s1.getAge(), s2.getAge());
    }
    public int compareByAgeNotStatic(Student s1, Student s2) {
        return Integer.compare(s1.getAge(), s2.getAge());
    }
}
