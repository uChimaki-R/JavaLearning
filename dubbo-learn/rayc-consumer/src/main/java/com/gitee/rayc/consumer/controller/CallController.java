package com.gitee.rayc.consumer.controller;

import com.gitee.rayc.common.service.InterServiceDemo;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-25
 * @Description: 用于测试远程调用的controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/test")
public class CallController {

    @DubboReference
    private InterServiceDemo service;

    @GetMapping
    public String getCall(@RequestParam String name) {
        return service.sayHello(name);
    }
}
