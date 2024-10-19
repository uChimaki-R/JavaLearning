package com.learning.netty.protocol.message;

import lombok.Data;

@Data
public class PingMessage extends Message {
    private final String username;

    public PingMessage(Integer sequenceId, String username) {
        super(sequenceId, MessageType.PingMessage);
        this.username = username;
    }
}
