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
            byte[] bytes = new byte[1024*64];
            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
            socket.receive(packet);
            String message = new String(packet.getData(), 0, packet.getLength());
            System.out.println("received: " + message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
