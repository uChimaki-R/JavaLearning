package com.pockyr.date;

import java.time.LocalDateTime;

public class DateTimeDemo {
    public static void main(String[] args) {
        LocalDateTime dateTime = LocalDateTime.now();

        LocalDateTime setTime = LocalDateTime.of(2099, 12, 23, 12, 11, 10);
        System.out.println(setTime);

        System.out.println(dateTime.getYear());

        LocalDateTime dateTime2 = dateTime.withYear(2000);
        System.out.println(dateTime2);

        LocalDateTime dateTime3 = dateTime2.minusMonths(-1);
        System.out.println(dateTime3);

        LocalDateTime dateTime4 = dateTime3.plusDays(10);
        System.out.println(dateTime4);

        System.out.println(dateTime4.isAfter(dateTime3));
    }
}
