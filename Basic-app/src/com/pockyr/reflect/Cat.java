package com.pockyr.reflect;

public class Cat {
    private String name;
    private int age;

    private Cat() {
    }

    public Cat(String name, int age) {
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

    private String saySomething(String something, int times){
        for(int i = 0; i<times; i++){
            System.out.println(something);
        }
        return something;
    }

}
