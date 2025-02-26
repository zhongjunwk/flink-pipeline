package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {

    private String appName;

    @Override
    public AppConfig clone() {
        try {
            return (AppConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return AppConfig.class;
    }
}