package com.gitee.rayc.curatorL.dynamicProp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-27
 * @Description: curator配置属性
 * @Version: 1.0
 */
@Data
@ConfigurationProperties("curator")
public class CuratorProperties {
    private String connectString;
    private int connectionTimeoutMs;
    private int sessionTimeoutMs;
    private int baseSleepTimeMs;
    private int maxRetries;

    private String namespace;
}
