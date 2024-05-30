package com.pockyr.net;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {
    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(8888)
        ) {
            System.out.println("----------ServerSocket started----------");
            // 等待连接
            Socket socket = serverSocket.accept();
            String address = socket.getInetAddress().getHostAddress();
            int port = socket.getPort();

            InputStream in = socket.getInputStream();
            DataInputStream dis = new DataInputStream(in);
            String message = dis.readUTF();

            System.out.println("received: " + message + " from: " + address + ":" + port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
