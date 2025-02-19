package com.zj.flink.pipeline.core.plugins;

import com.zj.flink.config.bean.FlinkPipelineConfig;

import java.io.Serializable;

public interface PipelinePlugin<T> extends Serializable {

    String getName();

    void setName(String name);

    void init(FlinkPipelineConfig flinkConfig);
}
