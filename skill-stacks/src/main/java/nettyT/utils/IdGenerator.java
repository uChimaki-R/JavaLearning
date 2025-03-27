package nettyT.utils;

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    private static final AtomicInteger id = new AtomicInteger(1);
    public static int nextId() {
        return id.getAndIncrement();
    }
}
