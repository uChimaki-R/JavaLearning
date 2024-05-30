package com.pockyr.net;

import java.io.IOException;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        try (
                // 未指定端口默认随机一个未使用的端口
                DatagramSocket socket = new DatagramSocket()
        ) {
            System.out.println("----------Client started----------");
            // 数据
            byte[] bytes = "Hello 哈哈你好？？".getBytes();
            // 打包，配置发送地址
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length, InetAddress.getLocalHost(), 6666);
            // 发送数据
            socket.send(packet);
            System.out.println("----------send----------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
