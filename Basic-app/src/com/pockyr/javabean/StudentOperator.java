package com.pockyr.javabean;

public class StudentOperator {
    private Student student;
    public StudentOperator(Student student) {
        this.student = student;
    }
    public boolean isOver18(){
        return this.student.getAge() > 18;
    }
    public void judge(){
        if(isOver18()){
            System.out.println(this.student.getName() + " is over 18");
        }
        else{
            System.out.println(this.student.getName() + " is not over 18");
        }
    }
}
