package com.gitee.rayc.curatorL.dynamicProp.config;

import com.gitee.rayc.curatorL.dynamicProp.properties.CuratorProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-27
 * @Description: 注入curator client
 * @Version: 1.0
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(CuratorProperties.class)
public class CuratorConfig {
    @Bean(destroyMethod = "close")
    public CuratorFramework curatorFramework(CuratorProperties curatorProperties) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(curatorProperties.getConnectString())
                .connectionTimeoutMs(curatorProperties.getConnectionTimeoutMs())
                .sessionTimeoutMs(curatorProperties.getSessionTimeoutMs())
                .retryPolicy(new ExponentialBackoffRetry(curatorProperties.getBaseSleepTimeMs(), curatorProperties.getMaxRetries()))
                .namespace(curatorProperties.getNamespace())
                .build();
        curatorFramework.start();
        log.info("CuratorFramework started, properties: {}", curatorProperties);
        return curatorFramework;
    }
}
