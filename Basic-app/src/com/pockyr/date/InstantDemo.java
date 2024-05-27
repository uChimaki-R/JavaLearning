package com.pockyr.date;

import java.time.Instant;

public class InstantDemo {
    public static void main(String[] args) {
        // 时间戳 从1970-01-01 00:00:00 到现在的间隔秒数和不足一秒的纳秒数
        Instant now = Instant.now();
        System.out.println(now);  // 2024-05-27T01:19:24.126109600Z
        System.out.println(now.getEpochSecond());
        System.out.println(now.getNano());

        Instant start = Instant.now();
        // ...运行代码...
        Instant end = Instant.now();
    }
}
