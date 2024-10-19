package com.learning.netty.protocol;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceIdGenerator {
    private static final AtomicInteger SEQUENCE_ID = new AtomicInteger(0);

    public static int nextSequenceId() {
        return SEQUENCE_ID.incrementAndGet();
    }
}
