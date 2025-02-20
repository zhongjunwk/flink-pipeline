package com.zj.flink.pipeline.core.config.bean;

import com.zj.flink.config.bean.annotation.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@FlinkPipelineConfiguration("pipelineConfig")
public class PipelineConfig implements Serializable, Cloneable {

    private String pluginPackages;
    private Map<String, PipelineConfig.PipelineServerProperties> pipelines;

    @Override
    public PipelineConfig clone() {
        try {
            return (PipelineConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Data
    public static class PipelineServerProperties {

        private String input;

        private String output;

        private List<String> plugins;
    }
}