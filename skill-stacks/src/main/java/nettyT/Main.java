package nettyT;

import nettyT.rpc.interfaces.HelloInterface;

public class Main {
    public static void main(String[] args) {
        // 使用代理来提升用户体验
        HelloInterface proxy = RpcClientManager.getProxy(HelloInterface.class);
        System.out.println(proxy.sayHello("Li si"));
//        System.out.println(proxy.sayHello("Wang wu"));
//        System.out.println(proxy.sayHello("Zhao liu"));
    }
}
