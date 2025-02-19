package com.zj.flink.pipeline.core.plugins;

import com.zj.flink.config.bean.FlinkConfig;

import java.io.Serializable;

public interface PipelinePlugin<T> extends Serializable {

    String getName();

    void setName(String name);

    void init(FlinkConfig flinkConfig);
}
