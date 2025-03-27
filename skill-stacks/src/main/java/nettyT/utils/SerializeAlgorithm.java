package nettyT.utils;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.charset.StandardCharsets;

@Slf4j
public enum SerializeAlgorithm implements Serializer {
    JAVA {
        @Override
        public byte[] encode(Object obj) {
            try (
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos)
            ) {
                oos.writeObject(obj);
                return bos.toByteArray();
            } catch (Exception e) {
                log.error("Encode error. Type: {}", JAVA.name());
                throw new RuntimeException(e);
            }
        }

        @Override
        public <T> T decode(byte[] bytes, Class<T> clazz) {
            try (
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis)
            ) {
                Object object = ois.readObject();
                return clazz.cast(object);
            } catch (Exception e) {
                log.error("Decode error. Type: {}", JAVA.name());
                throw new RuntimeException(e);
            }
        }
    },
    FAST_JSON {
        @Override
        public byte[] encode(Object obj) {
            return JSON.toJSONString(obj).getBytes(StandardCharsets.UTF_8);
        }

        @Override
        public <T> T decode(byte[] bytes, Class<T> clazz) {
            return JSON.parseObject(new String(bytes, StandardCharsets.UTF_8), clazz);
        }
    }
}
