package com.pockyr.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetDemo {
    public static void main(String[] args) {
        try {
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println(ip.getHostAddress());
            System.out.println(ip.getHostName());

            InetAddress ip2 = InetAddress.getByName("www.bilibili.com");
            System.out.println(ip2.getHostAddress());
            System.out.println(ip2.getHostName());

            // ping
            System.out.println(ip2.isReachable(5000));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
