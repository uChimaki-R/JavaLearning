package com.learning.netty.protocol.domain;

import lombok.*;

@Data
public class LoginResponseMessage extends Message {
    private  Boolean success;

    public LoginResponseMessage(Boolean success) {
        super(1, 1);
        this.success = success;
    }
}
