package com.pockyr.stream;

import java.io.*;

public class DataStream {
    public static void main(String[] args) {
        try (
                DataInputStream dis = new DataInputStream(
                        new BufferedInputStream(new FileInputStream("Basic-app\\src\\data.txt")))
        ) {
            // 读入
            System.out.println(dis.readInt());
            System.out.println(dis.readUTF());
            System.out.println(dis.readDouble());
            System.out.println(dis.readBoolean());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
