package nettyT.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class DemoMsg implements Serializable {
    private Integer messageId;
    private Integer messageType;

    protected static final int RPC_REQUEST = 1;
    protected static final int RPC_RESPONSE = 2;

    public abstract Integer getMessageType();

    private static final Map<Integer, Class<? extends DemoMsg>> TYPE_CLASS_MAP = new HashMap<>();

    public static Class<? extends DemoMsg> getMsgClass(int messageType) {
        return TYPE_CLASS_MAP.get(messageType);
    }

    static {
        TYPE_CLASS_MAP.put(RPC_REQUEST, RpcRequestMsg.class);
        TYPE_CLASS_MAP.put(RPC_RESPONSE, RpcResponseMsg.class);
    }
}
