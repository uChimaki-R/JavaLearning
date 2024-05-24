package com.pockyr.arraylist;

public class Food {
    private String foodName;
    private double foodPrice;
    private String description;

    public Food(){}

    public Food(String foodName, double foodPrice, String description) {
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.description = description;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(int foodPrice) {
        this.foodPrice = foodPrice;
    }
}
