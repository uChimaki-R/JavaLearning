package com.learning.netty.protocol.message;

import lombok.Data;

@Data
public class RpcResponseMessage extends Message{
    private final Boolean success;
    private final Object result;
    private final String error;
    public RpcResponseMessage(Integer sequenceId, Boolean success, String error, Object result) {
        super(sequenceId, MessageType.RpcResponseMessage);
        this.success = success;
        this.result = result;
        this.error = error;
    }
}
