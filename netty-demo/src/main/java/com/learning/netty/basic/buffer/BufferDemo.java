package com.learning.netty.basic.buffer;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    public static void main(String[] args) {
        // 使用buffer从文件流的channel读取数据
        try (
                FileChannel fileChannel = new FileInputStream("D:\\Code\\JavaCode\\JavaLearning\\netty-demo\\src\\main\\resources\\stacks.md").getChannel()
        ) {
            ByteBuffer buffer = ByteBuffer.allocate(3);
            while (fileChannel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    System.out.print((char) buffer.get());
                }
                System.out.println();
                buffer.clear();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
