package com.learning.netty.protocol.message;

import lombok.Data;

@Data
public class RpcRequestMessage extends Message{
    private final String interfaceName;
    private final String methodName;
    private final Class<?>[] parameterTypes;
    private final Object[] params;

    public RpcRequestMessage(Integer sequenceId, String interfaceName, String methodName, Class<?>[] parameterTypes, Object[] params) {
        super(sequenceId, MessageType.RpcRequestMessage);
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.parameterTypes = parameterTypes;
        this.params = params;
    }
}
