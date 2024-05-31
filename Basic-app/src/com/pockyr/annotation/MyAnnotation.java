package com.pockyr.annotation;

// 本质是继承了Annotation接口的接口
public @interface MyAnnotation {
    // 本质是 public abstract String value();
    String value();

    int id() default 10;
}
