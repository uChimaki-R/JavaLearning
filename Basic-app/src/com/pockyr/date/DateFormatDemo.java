package com.pockyr.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateFormatDemo {
    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        LocalDateTime now = LocalDateTime.now();
        // 双向可用
        System.out.println(now.format(dtf));
        System.out.println(dtf.format(now));

        String timeStr = "2099年12月03日";
        LocalDate parseTime = LocalDate.parse(timeStr, dtf);
        System.out.println(parseTime);
    }
}
