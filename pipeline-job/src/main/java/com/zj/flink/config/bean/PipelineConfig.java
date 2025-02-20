package com.zj.flink.config.bean;

import com.zj.flink.config.bean.annotation.FlinkPipelineConfiguration;
import com.zj.flink.pipeline.core.config.PipelineProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@FlinkPipelineConfiguration("pipelineConfig")
public class PipelineConfig extends PipelineProperties {

    @Override
    public PipelineProperties clone() {
        return (PipelineProperties) super.clone();
    }
}
