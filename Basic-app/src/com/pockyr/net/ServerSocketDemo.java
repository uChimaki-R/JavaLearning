package com.pockyr.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSocketDemo {
    public static void main(String[] args) {
        try (
                ServerSocket serverSocket = new ServerSocket(8888)
        ) {
            System.out.println("----------ServerSocket started----------");
            while (true) {
                // 等待连接
                Socket socket = serverSocket.accept();
                // 每个连接单独处理
                new Thread(new SocketManageThread(socket)).start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
