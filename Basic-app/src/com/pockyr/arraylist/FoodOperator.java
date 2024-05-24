package com.pockyr.arraylist;

import java.util.ArrayList;
import java.util.Scanner;

public class FoodOperator {
    private ArrayList<Food> foodList = new ArrayList<>();

    public FoodOperator() {
    }

    public void showAllFoods() {
        if (foodList.isEmpty()) {
            System.out.println("暂无菜品");
        } else {
            System.out.println("---------所有菜品---------");
            for (Food food : this.foodList) {
                System.out.println("菜名: " + food.getFoodName());
                System.out.println("菜价: " + food.getFoodPrice());
                System.out.println("菜品描述: " + food.getDescription());
                System.out.println("-------------------------");
            }
        }
    }

    public void addFood() {
        Scanner sc = new Scanner(System.in);
        System.out.print("输入菜名: ");
        String foodName = sc.next();
        System.out.print("输入菜价: ");
        double foodPrice = sc.nextDouble();
        System.out.print("输入菜品描述: ");
        String description = sc.next();
        System.out.println("添加菜品成功");
        Food food = new Food(foodName, foodPrice, description);
        this.foodList.add(food);
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("1.\t添加菜品\n2.\t展示菜单\n3.\t退出\n输入: ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    this.addFood();
                    break;
                case 2:
                    this.showAllFoods();
                    break;
                default:
                    System.out.println("感谢使用");
                    return;
            }
        }
    }
}
