package com.learning.netty.protocol;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

public class MyFrameDecoder extends LengthFieldBasedFrameDecoder {
    public MyFrameDecoder() {
        super(1024, 12, 4, 0, 0);
    }
}
