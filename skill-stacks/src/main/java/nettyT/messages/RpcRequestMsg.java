package nettyT.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcRequestMsg extends DemoMsg{
    @Override
    public Integer getMessageType() {
        return RPC_REQUEST;
    }

    private String interfaceName;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] params;
}
