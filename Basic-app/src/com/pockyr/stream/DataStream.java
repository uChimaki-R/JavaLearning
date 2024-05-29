package com.pockyr.stream;

import java.io.*;

public class DataStream {
    public static void main(String[] args) {
        try (
                DataOutputStream dos = new DataOutputStream(
                        new BufferedOutputStream(new FileOutputStream("Basic-app\\src\\data.txt")))
        ) {
            dos.writeInt(10);
            dos.writeUTF("Hello World");
            dos.writeDouble(3.1415);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
