package com.learning.netty.protocol.message;

import lombok.Data;

@Data
public class LoginRequestMessage extends Message {
    private final String username;
    private final String password;

    public LoginRequestMessage(Integer sequenceId, String username, String password) {
        super(sequenceId, MessageType.LoginRequestMessage);
        this.username = username;
        this.password = password;
    }
}
