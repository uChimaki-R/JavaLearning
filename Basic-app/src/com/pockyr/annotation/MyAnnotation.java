package com.pockyr.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.CONSTRUCTOR})  // 元注解Target指明注解的使用范围，如METHOD是用于方法
@Retention(RetentionPolicy.RUNTIME)  // 元注解Retention指明注解的存活周期，RUNTIME是直到运行都存活
// 本质是继承了Annotation接口的接口
public @interface MyAnnotation {
    // 本质是 public abstract String value();
    String value();

    int id() default 10;
}
