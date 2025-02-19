package com.zj.flink.pipeline.core.plugins;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class AbstractPipelinePlugin<T> implements PipelinePlugin<T> {
    private String name;
}
