package com.learning.netty.basic.multi;

import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class ClientChannel {
    public static void main(String[] args) throws Exception {
        try (SocketChannel sc = SocketChannel.open()) {
            sc.connect(new InetSocketAddress("127.0.0.1", 8080));
            sc.write(Charset.defaultCharset().encode("hello"));
            int read = System.in.read();
        } catch (Exception e) {
            throw new Exception(e);
        }
    }
}
