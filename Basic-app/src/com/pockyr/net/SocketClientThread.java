package com.pockyr.net;

import java.io.DataInputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketException;

public class SocketClientThread implements Runnable{
    private final Socket socket;
    public SocketClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (
                InputStream in = socket.getInputStream();
                DataInputStream dis = new DataInputStream(in)
        ) {
            // 不断接受服务端转发过来的数据并打印到自己的客户端界面
            while (true) {
                try {
                    String message = dis.readUTF();
                    System.out.println(message);
                } catch (EOFException | SocketException e) {
                    System.out.println("self-exit");
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
