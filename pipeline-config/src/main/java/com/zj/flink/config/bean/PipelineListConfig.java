package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PipelineListConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {

    private List<PipelineConfig> pipelineConfigList;

    @Override
    public PipelineListConfig clone() {
        try {
            return (PipelineListConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return PipelineListConfig.class;
    }
}