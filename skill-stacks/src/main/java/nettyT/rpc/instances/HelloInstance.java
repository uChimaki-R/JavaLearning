package nettyT.rpc.instances;

import nettyT.rpc.interfaces.HelloInterface;

public class HelloInstance implements HelloInterface {
    @Override
    public String sayHello(String to) {
//        int i = 1 / 0;
        return "Hello " + to + ", Hello World!";
    }
}
