package com.zj.flink.pipeline.core.config;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class PipelineProperties implements Serializable, Cloneable {

    private String pluginPackages;

    private Map<String, PipelineServerProperties> pipelines;

    @Override
    public PipelineProperties clone() {
        try {
            return (PipelineProperties) super.clone();
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