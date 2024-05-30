package com.pockyr.net;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class SocketManageThread implements Runnable {
    private final Socket socket;

    public SocketManageThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String address = socket.getInetAddress().getHostAddress();
        int port = socket.getPort();
        System.out.println("connect from: " + address + ":" + port);
        try (
                InputStream in = socket.getInputStream();
                DataInputStream dis = new DataInputStream(in)
        ) {
            while (true) {
                try {
                    // 客户端主动断开连接时会发生EOFException异常，捕获代表连接断开
                    String message = dis.readUTF();
                    System.out.println("received: " + message + " from: " + address + ":" + port);
                } catch (EOFException e) {
                    System.out.println("exit: " + address + ":" + port);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
