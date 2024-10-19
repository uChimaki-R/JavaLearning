package com.learning.netty.protocol.domain;

import lombok.Data;

@Data
public class PingMessage extends Message {
    private final String username;

    public PingMessage(String username) {
        super(2, 2);
        this.username = username;
    }
}
