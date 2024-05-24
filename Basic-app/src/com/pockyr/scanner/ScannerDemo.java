package com.pockyr.scanner;

import java.util.Scanner;

public class ScannerDemo {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter a int: ");
        int num = sc.nextInt();
        System.out.println("int: " + num);
        System.out.print("Enter a String: ");
        String str = sc.next();
        System.out.println("String: " + str);
    }
}
