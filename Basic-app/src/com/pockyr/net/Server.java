package com.pockyr.net;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Server {
    public static void main(String[] args) {
        try (
                DatagramSocket socket = new DatagramSocket(6666)
        ) {
            System.out.println("----------Server started----------");
            while (true) {
                // UDP规定一个数据包不超过64KB
                byte[] bytes = new byte[1024 * 64];
                DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                socket.receive(packet);
                // 接多少打印多少
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("received: " + message + " from: " + packet.getAddress() + ":" + packet.getPort());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
