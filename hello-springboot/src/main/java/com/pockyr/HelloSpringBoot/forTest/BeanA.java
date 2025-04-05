package com.pockyr.HelloSpringBoot.forTest;

import com.pockyr.HelloSpringBoot.forTest.inter.IBeanA;
import com.pockyr.HelloSpringBoot.forTest.inter.IBeanB;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-04-03
 * @Description: Bean A
 * @Version: 1.0
 */
@Component
public class BeanA implements IBeanA {
    @Resource
    private IBeanB beanB;

    @PostConstruct
    public void init(){
        System.out.println("BeanA init");
        beanB.sayHello();
    }

    @Override
    public void sayHello() {
        System.out.println("BeanA sayHello");
    }
}
