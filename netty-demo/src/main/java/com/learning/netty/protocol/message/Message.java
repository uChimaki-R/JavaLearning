package com.learning.netty.protocol.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    public Integer sequenceId;
    public MessageType messageType;

    public static Class<?> getMessageClass(MessageType type) {
        switch (type) {
            case LoginRequestMessage:
                return LoginRequestMessage.class;
            case LoginResponseMessage:
                return LoginResponseMessage.class;
            case PingMessage:
                return PingMessage.class;
            case RpcRequestMessage:
                return RpcRequestMessage.class;
            case RpcResponseMessage:
                return RpcResponseMessage.class;
        }
        return null;
    }
}