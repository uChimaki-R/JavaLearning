package com.pockyr.date;

import java.time.Clock;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ZoneDemo {
    public static void main(String[] args) {
        System.out.println(ZoneId.getAvailableZoneIds());

        ZoneId zid = ZoneId.systemDefault();
        System.out.println(zid);

        ZonedDateTime zdt = ZonedDateTime.now(zid);
        System.out.println(zdt);

        ZonedDateTime zdt2 = ZonedDateTime.now(Clock.systemUTC());
        System.out.println(zdt2);
    }
}
