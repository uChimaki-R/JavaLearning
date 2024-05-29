package com.pockyr.stream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class ObjectStream {
    public static void main(String[] args) {
        try (
                ObjectOutputStream oos = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream("Basic-app\\src\\objects.txt")))
        ) {
            oos.writeObject(new Student("张三", 14));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
