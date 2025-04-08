package com.gitee.rayc.curatorL.dynamicProp.properties;

import com.gitee.rayc.curatorL.dynamicProp.anno.MarkRefresh;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-27
 * @Description: 测试会刷新属性值的配置属性类
 * @Version: 1.0
 */
@Data
@Component
@ConfigurationProperties("refresh")
public class RefreshProperties {
    @MarkRefresh
    public String value1;

    @MarkRefresh
    public String value2;
}
