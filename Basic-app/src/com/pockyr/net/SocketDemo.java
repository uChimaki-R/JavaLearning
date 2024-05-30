package com.pockyr.net;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketDemo {
    public static void main(String[] args) {
        try (
                Socket socket = new Socket(InetAddress.getLocalHost(), 8888)
        ) {
            System.out.println("----------ClientSocket started----------");
            Scanner scanner = new Scanner(System.in);
            try (
                    OutputStream out = socket.getOutputStream();
                    // 使用数据流包装
                    DataOutputStream dos = new DataOutputStream(out)
            ) {
                while (true) {
                    System.out.print("input: ");
                    String message = scanner.nextLine();
                    if (message.equals("exit")) {
                        System.out.println("----------exit----------");
                        break;
                    }
                    dos.writeUTF(message);
                    // 使用flush及时送出数据
                    dos.flush();
                    System.out.println("----------send----------");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
