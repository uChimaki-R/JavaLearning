package com.gitee.rayc.curatorL.dynamicProp.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: Ray-C
 * @CreateTime: 2025-03-27
 * @Description: 标记动态更新的属性，测试默认属性类型为String（否则反射的类型转换还要序列化反序列化）
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface MarkRefresh {
}
