package com.learning.netty.protocol.domain;

import lombok.Data;

@Data
public class LoginResponseMessage extends Message {
    private final Boolean success;

    public LoginResponseMessage(Boolean success) {
        super(1, 1);
        this.success = success;
    }
}
