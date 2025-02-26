package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class PipelineConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {

    private String pluginPackages;
    private int parallelism = 1;
    private Map<String, PipelineConfig.PipelineServerProperties> pipelines;

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
    public static class PipelineServerProperties implements Serializable, Cloneable {

        private String input;

        private String output;

        private List<String> plugins;

        @Override
        public PipelineServerProperties clone() {
            try {
                return (PipelineServerProperties) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}