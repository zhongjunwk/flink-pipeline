package com.zj.flink.config.bean;

import com.zj.flink.config.bean.annotation.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@FlinkPipelineConfiguration("sensitiveDataConfig")
public class SensitiveDataConfig implements Serializable, Cloneable {
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
}
