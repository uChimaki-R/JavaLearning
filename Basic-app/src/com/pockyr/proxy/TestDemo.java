package com.pockyr.proxy;

public class TestDemo {
    public static void main(String[] args) {
        Star star = new BigStar("Lisa");
        System.out.println(star.sing("\"Good night\""));
        star.dance();

        System.out.println("-----------------------------");

        Star starProxy = ProxyUtils.getStarProxy(star);
        System.out.println(starProxy.sing("\"Good night\""));
        starProxy.dance();

//      Lisa is singing "Good night"
//      thanks!
//      Lisa is dancing
//      -----------------------------
//      do something before singing
//      Lisa is singing "Good night"
//      do something after singing
//      thanks!
//      do something before dancing
//      Lisa is dancing
//      do something after dancing
    }
}
