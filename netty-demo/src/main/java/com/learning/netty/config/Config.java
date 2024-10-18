package com.learning.netty.config;

import com.learning.netty.protocol.Algorithm;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    static final Properties properties;

    static {
        properties = new Properties();
        InputStream in = Config.class.getClassLoader().getResourceAsStream("config.properties");
        try {
            properties.load(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Algorithm getAlgorithm() {
        String algorithm = properties.getProperty("algorithm");
        if (algorithm == null) {
            return Algorithm.Java;
        }
        return Algorithm.valueOf(algorithm);
    }
}
