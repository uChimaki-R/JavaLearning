package com.pockyr.regex;

public class TestDemo {
    public static void main(String[] args) {
        String phoneNumbers = "136-555-6666";
        if (phoneNumbers.matches("1\\d{2}-\\d{3}-\\d{4}")) {
            System.out.println("Valid phone number");
        }
        else{
            System.out.println("Invalid phone number");
        }
    }
}
