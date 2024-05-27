package com.pockyr.date;

import java.time.LocalDate;
import java.time.Period;

public class PeriodDemo {
    public static void main(String[] args) {
        // 一段日期之间的年月日间隔
        LocalDate startDate = LocalDate.of(2018, 1, 1);
        LocalDate endDate = LocalDate.of(2018, 12, 31);
        Period period = Period.between(startDate, endDate);
        System.out.println(period.getDays());
    }
}
