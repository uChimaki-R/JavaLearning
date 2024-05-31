package com.pockyr.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyUtils {
    public static Star getStarProxy(Star star) {
        // 使用Proxy.newProxyInstance获取代理
        // 参数共三个
        // 1、获取类加载器，一般使用本工具类获取，写法固定
        // 2、类列表，指明代理的执行功能
        // 3、指明功能执行的具体方式，即对原先方法的完善
        return (Star) Proxy.newProxyInstance(
                ProxyUtils.class.getClassLoader(), new Class[]{Star.class}, new InvocationHandler() {
                    @Override  // 这是一个回调方法，即在代理使用 new Class[]{Star.class} 中Star声明的方法时，实际上会使用该方法并传递参数
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        // 参数有三: 1、proxy为该代理 2、method为该代理的某个方法 3、args为该方法传递的参数
                        if (method.getName().equals("sing")) {
                            System.out.println("do something before singing");
                            Object result = method.invoke(star, args);
                            System.out.println("do something after singing");
                            return result;
                        }
                        else if (method.getName().equals("dance")) {
                            System.out.println("do something before dancing");
                            Object result = method.invoke(star, args);
                            System.out.println("do something after dancing");
                            return result;
                        }
                        else {
                            return method.invoke(star, args);
                        }
                    }
                }
        );
    }
}
