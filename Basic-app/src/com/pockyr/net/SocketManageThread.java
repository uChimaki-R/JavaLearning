package com.pockyr.net;

import java.io.*;
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
                    // 读到客户端发来的消息后需要转发到所有的客户端上
                    System.out.println("received: " + message + " from: " + address + ":" + port);
                    // 给message加上发送者的信息
                    message = address + ":" + port + ": " + message;
                    forwardMsgToAll(message);
                } catch (EOFException e) {
                    // 删除离线的客户端
                    ServerSocketDemo.sockets.remove(socket);
                    System.out.println("exit: " + address + ":" + port);
                    break;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void forwardMsgToAll(String msg) throws IOException {
        // 遍历所有的客户端，转发消息
        for (Socket sc : ServerSocketDemo.sockets) {
            // 获取输出流
            // 这里不能主动关闭流，不然会让连接断开
            OutputStream outputStream = sc.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(outputStream);
            // 输出数据
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        }
    }
}
