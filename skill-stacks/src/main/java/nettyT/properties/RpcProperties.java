package nettyT.properties;

import nettyT.utils.SerializeAlgorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RpcProperties {
    static Properties props;

    static {
        props = new Properties();
        try (
                InputStream is = RpcProperties.class.getClassLoader().getResourceAsStream("rpc.properties");
        ) {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static SerializeAlgorithm getSerializeAlgorithm() {
        String algorithm = props.getProperty("rpc.codec.algorithm");
        if (algorithm == null) {
            return SerializeAlgorithm.JAVA;
        }
        return SerializeAlgorithm.valueOf(algorithm);
    }
}
