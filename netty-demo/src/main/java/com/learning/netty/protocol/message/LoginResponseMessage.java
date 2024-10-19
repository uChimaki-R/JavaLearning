package com.learning.netty.protocol.message;

import lombok.Data;

@Data
public class LoginResponseMessage extends Message {
    private final Boolean success;

    public LoginResponseMessage(Integer sequenceId, Boolean success) {
        super(sequenceId, MessageType.LoginResponseMessage);
        this.success = success;
    }
}
