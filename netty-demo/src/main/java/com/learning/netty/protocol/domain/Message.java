package com.learning.netty.protocol.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer type;
    public Integer sequenceId;

    public static Class<?> getMessageClass(Integer type) {
        switch (type) {
            case 0:
                return LoginRequestMessage.class;
            case 1:
                return LoginResponseMessage.class;
            case 2:
                return PingMessage.class;
        }
        return null;
    }
}
