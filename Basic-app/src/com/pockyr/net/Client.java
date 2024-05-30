package com.pockyr.net;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try (
                // 未指定端口默认随机一个未使用的端口
                DatagramSocket socket = new DatagramSocket()
        ) {
            System.out.println("----------Client started----------");
            Scanner in = new Scanner(System.in);
            while (true) {
                // 数据
                System.out.print("input: ");
                String input = in.nextLine();
                if (input.equals("exit")) {
                    System.out.println("----------exit----------");
                    break;
                }
                byte[] bytes = input.getBytes();
                // 打包，配置发送地址
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 6666);
                // 发送数据
                socket.send(packet);
                System.out.println("----------send----------");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
