package com.pockyr.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// 需要有Data和Component注解
@Data
@Component
@ConfigurationProperties(prefix = "qiniu.kodo") // prefix中开头只能小写
public class QiNiuKodoProperties {
    private String accessKey;
    private String secretKey;
    private String bucket;
    private String folder;
    private String domain;
}
