package nettyT.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RpcResponseMsg extends DemoMsg {
    private Object result;
    private String exceptionMessage;

    @Override
    public Integer getMessageType() {
        return RPC_RESPONSE;
    }
}
