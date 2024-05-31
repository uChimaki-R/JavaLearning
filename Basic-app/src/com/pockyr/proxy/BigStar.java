package com.pockyr.proxy;

public class BigStar implements Star {
    public String name;
    BigStar(String name) {
        this.name = name;
    }
    @Override
    public String sing(String name) {
        System.out.println(this.name + " is singing " + name);
        return "thanks!";
    }

    @Override
    public void dance() {
        System.out.println(this.name + " is dancing");
    }
}
