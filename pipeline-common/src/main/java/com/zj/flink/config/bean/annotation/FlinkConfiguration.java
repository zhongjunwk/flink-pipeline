package com.zj.flink.config.bean.annotation;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FlinkConfiguration {
    String value();
}