package com.pockyr.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class SocketDemo {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 8888)
        ) {
            System.out.println("----------ClientSocket started----------");
            OutputStream out = socket.getOutputStream();
            // 使用数据流包装
            DataOutputStream dos = new DataOutputStream(out);
            dos.writeUTF("Hello World");
            // 使用flush及时送出数据
            dos.flush();
            System.out.println("----------send----------");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
