package com.pockyr.stream;

import java.io.*;

public class IOStreamDemo {
    public static void main(String[] args) {
        try (
                InputStream inputStream = new FileInputStream("Basic-app\\src\\data.txt");
                // 使用缓冲区包装
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        ) {
            byte[] bytes = new byte[1024];
            int len;  // 用len记录读取的长度，不然不足1024字节的地方会打印乱码
            while((len = bufferedInputStream.read(bytes)) != -1) {
                System.out.println(new String(bytes, 0, len));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
