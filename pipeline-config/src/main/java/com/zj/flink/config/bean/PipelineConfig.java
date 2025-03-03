package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PipelineConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {

    private String pluginPackages;
    private String pipelineName;
    private Boolean disableChaining = false;
    private PipelineConfig.PipelineProperties pipeline;

    @Override
    public PipelineConfig clone() {
        try {
            return (PipelineConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return PipelineConfig.class;
    }

    @Data
    public static class PipelineProperties implements Serializable, Cloneable {

        private String input;

        private String output;

        private List<String> plugins;

        @Override
        public PipelineProperties clone() {
            try {
                return (PipelineProperties) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}