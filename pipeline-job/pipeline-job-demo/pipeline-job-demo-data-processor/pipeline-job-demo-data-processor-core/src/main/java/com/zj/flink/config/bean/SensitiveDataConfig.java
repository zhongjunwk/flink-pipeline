package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SensitiveDataConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private List<String> sensitiveDataList;

    @Override
    public SensitiveDataConfig clone() {
        try {
            return (SensitiveDataConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return SensitiveDataConfig.class;
    }
}
