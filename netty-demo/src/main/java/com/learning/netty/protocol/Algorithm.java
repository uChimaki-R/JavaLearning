package com.learning.netty.protocol;

import com.alibaba.fastjson.JSON;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public enum Algorithm implements Serializer {
    Java {
        @Override
        public <T> byte[] serialize(T obj) {
            try (ByteOutputStream bos = new ByteOutputStream()) {
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                oos.writeObject(obj);
                return bos.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public <T> T deserialize(byte[] bytes, Class<T> clz) {
            try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
                return clz.cast(ois.readObject());
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    },
    Json {
        @Override
        public <T> byte[] serialize(T obj) {
            return JSON.toJSONBytes(obj);
        }

        @Override
        public <T> T deserialize(byte[] bytes, Class<T> clz) {
            return JSON.parseObject(bytes, clz);
        }
    }
}
