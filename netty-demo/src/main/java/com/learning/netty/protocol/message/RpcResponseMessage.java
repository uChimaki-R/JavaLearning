package com.learning.netty.protocol.message;

import lombok.Data;

@Data
public class RpcResponseMessage extends Message{
    private final Boolean success;
    private final String error;
    public RpcResponseMessage(Integer sequenceId, Boolean success, String error) {
        super(sequenceId, MessageType.RpcResponseMessage);
        this.success = success;
        this.error = error;
    }
}
