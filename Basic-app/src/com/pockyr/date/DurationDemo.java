package com.pockyr.date;

import java.time.Duration;
import java.time.LocalDateTime;

public class DurationDemo {
    public static void main(String[] args) {
        LocalDateTime startTime = LocalDateTime.of(2003, 12, 12, 12, 12, 12);
        LocalDateTime endTime = LocalDateTime.now();
        Duration duration = Duration.between(startTime, endTime);

        System.out.println(duration);
        System.out.println(duration.toMillis());
    }
}
