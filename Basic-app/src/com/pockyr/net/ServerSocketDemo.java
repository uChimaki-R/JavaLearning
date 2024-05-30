package com.pockyr.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerSocketDemo {
    // 存储连接的所有客户端，用于消息的转发
    public static ArrayList<Socket> sockets = new ArrayList<>();
    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(8888)
        ) {
            System.out.println("----------ServerSocket started----------");
            while (true) {
                // 等待连接
                Socket socket = serverSocket.accept();
                sockets.add(socket);
                // 每个连接单独处理
                new Thread(new SocketManageThread(socket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
