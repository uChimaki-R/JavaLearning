package com.pockyr.stream;

import java.io.*;

public class ObjectStream {
    public static void main(String[] args) {
        try (
                ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream("Basic-app\\src\\objects.txt")))
        ) {
            Student student;
            while (true) {
                try {
                    // 尝试读取对象
                    student = (Student) ois.readObject();
                    // 处理读取到的对象
                    System.out.println(student);
                } catch (EOFException e) {
                    // 文件结束
                    System.out.println("Reached end of file.");
                    break;
                } catch (ClassNotFoundException e) {
                    // 无法找到对象的类定义
                    System.err.println("Class not found: " + e.getMessage());
                    break;
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
