package com.qiniu;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // 自动配置类, 配置需要加载的bean对象
@EnableConfigurationProperties(QiNiuKodoProperties.class) // 由于Utils中的变量qiNiuKodoProperties原本是使用自动注入的，所以去掉Component注解后需要在这里将QiNiuKodoProperties交给IOC容器管理
public class QiNiuKodoAutoConfiguration {
    @Bean
    public QiNiuKodoUtils qiNiuKodoUtils(QiNiuKodoProperties qiNiuKodoProperties) { // 然后在参数中声明该bean对象后就可以自动注入了
        QiNiuKodoUtils utils = new QiNiuKodoUtils();
        utils.setQiNiuKodoProperties(qiNiuKodoProperties); // 使用注入的对象赋值
        return utils;
    }
}
