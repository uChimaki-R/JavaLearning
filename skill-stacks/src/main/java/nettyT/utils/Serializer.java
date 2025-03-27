package nettyT.utils;

public interface Serializer {
    byte[] encode(Object obj);

    <T> T decode(byte[] bytes, Class<T> clazz);
}
