package com.gitee.rayc.provider.services;

import com.gitee.rayc.common.service.InterServiceDemo;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-25
 * @Description: 远程调用service实现类
 * @Version: 1.0
 */
@DubboService
public class ImplServiceDemo implements InterServiceDemo {
    @Override
    public String sayHello(String name) {
        return "ImplServiceDemo say hello to " + name;
    }
}
