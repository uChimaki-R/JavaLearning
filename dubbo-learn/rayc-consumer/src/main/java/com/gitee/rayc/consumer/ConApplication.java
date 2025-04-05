package com.gitee.rayc.consumer;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-25
 * @Description: 服务调用者启动类
 * @Version: 1.0
 */
@SpringBootApplication
@EnableDubbo
public class ConApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConApplication.class, args);
    }
}
