package com.learning.netty.protocol;

public interface Serializer {
    <T> byte[] serialize(T obj);

    <T> T deserialize(byte[] bytes, Class<T> clz);
}
