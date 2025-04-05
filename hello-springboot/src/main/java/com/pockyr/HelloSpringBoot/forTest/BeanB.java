package com.pockyr.HelloSpringBoot.forTest;

import com.pockyr.HelloSpringBoot.forTest.inter.IBeanA;
import com.pockyr.HelloSpringBoot.forTest.inter.IBeanB;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: Bean B
 * @Version: 1.0
 */
@Component
public class BeanB implements IBeanB {
    @Resource
    private IBeanA beanA;

    @PostConstruct
    @Async
    public void init(){
        System.out.println("BeanB init");
        beanA.sayHello();
    }

    @Override
    public void sayHello() {
        System.out.println("BeanB sayHello");
    }
}
