package com.gitee.rayc.curatorL.dynamicProp.controller;

import com.gitee.rayc.curatorL.dynamicProp.properties.RefreshProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-27
 * @Description: 测试controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/test")
public class DemoController {
    @Resource
    private RefreshProperties refreshProperties;

    // 手动修改配置后调用test，配置就会热更新
    @GetMapping
    public String test() {
        return "value now is " + refreshProperties;
    }
}
