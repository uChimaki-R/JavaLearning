package com.learning.netty.protocol.domain;

import lombok.Data;

@Data
public class LoginRequestMessage extends Message {
    private final String username;
    private final String password;

    public LoginRequestMessage(String username, String password) {
        super(0, 0);
        this.username = username;
        this.password = password;
    }
}
