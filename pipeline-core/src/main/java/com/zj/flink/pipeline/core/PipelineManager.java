package com.zj.flink.pipeline.core;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ClassUtil;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.config.PipelineProperties;
import com.zj.flink.pipeline.core.plugins.PipelinePlugin;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class PipelineManager<Record> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private PipelineProperties pipelineProperties;

    private Map<String, Pipeline<Record>> allPipelines;

    public void start(FlinkPipelineConfig flinkConfig, StreamExecutionEnvironment env) {
        this.pipelineProperties = flinkConfig.getConfig(PipelineProperties.class);
        this.init(flinkConfig);
        this.allPipelines.forEach((name, pipeline) -> pipeline.start(env));
    }

    private Set<Class<?>> getPlugins(String pluginsScanPackage) {
        if (StringUtils.isEmpty(pluginsScanPackage)) {
            return new HashSet<>();
        }
        Set<Class<?>> classes = ClassUtil.scanPackageByAnnotation(pluginsScanPackage, PluginComponent.class);
        if (CollectionUtil.isEmpty(classes)) {
            logger.warn("未找到任何插件");
            return new HashSet<>();
        }
        return classes;
    }

    private Map<String, Class<Record>> createInstances() {
        Map<String, Class<Record>> instances = new ConcurrentHashMap<>();
        this.getPlugins(this.pipelineProperties.getPluginPackages()).forEach((instance) -> {
            if (!PipelinePlugin.class.isAssignableFrom(instance)) {
                String plugin = instance.getName();
                throw new UnsupportedOperationException(plugin + "未继承" + PipelinePlugin.class.getName());
            } else {
                String name = this.getPipelineName(instance.getAnnotation(PluginComponent.class).value());
                instances.put(name, (Class<Record>) instance);
            }
        });
        return instances;
    }

    private PipelinePlugin<Record> getAndCheck(String name, Map<String, Class<Record>> instances, FlinkPipelineConfig flinkConfig) {
        Class<PipelinePlugin<Record>> type = (Class<PipelinePlugin<Record>>) instances.get(this.getPipelineName(name));
        if (type == null) {
            throw new UnsupportedOperationException("未找到声明@" + PluginComponent.class.getName() + "(" + name + ")的实现类");
        } else {
            PipelinePlugin<Record> pipelinePlugin = null;
            try {
                pipelinePlugin = type.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
            pipelinePlugin.setName(name);
            pipelinePlugin.init(flinkConfig);
            return pipelinePlugin;
        }
    }

    private void init(FlinkPipelineConfig flinkConfig) {
        Map<String, Pipeline<Record>> pipelineMap = new HashMap<>();
        Map<String, Class<Record>> pluginMap = this.createInstances();
        this.pipelineProperties.getPipelines().forEach((pipelineName, property) -> {
            Pipeline<Record> pipeline = new Pipeline<>();
            pipeline.setName(pipelineName);
            pipeline.setInputPlugin(this.getAndCheck(property.getInput(), pluginMap, flinkConfig));
            if (CollectionUtil.isNotEmpty(property.getPlugins())) {
                List<PipelinePlugin<Record>> processPipelinePluginList = new ArrayList<>(property.getPlugins().size());
                property.getPlugins().forEach((pluginName) -> {
                    processPipelinePluginList.add(this.getAndCheck(pluginName, pluginMap, flinkConfig));
                });
                pipeline.setProcessPipelinePluginList(processPipelinePluginList);
            }
            pipeline.setOutputPlugin(this.getAndCheck(property.getOutput(), pluginMap, flinkConfig));
            pipelineMap.put(pipelineName, pipeline);
        });
        this.allPipelines = Collections.unmodifiableMap(pipelineMap);
    }

    private String getPipelineName(String pipelineName) {
        return "pipeline.plugin." + pipelineName;
    }
}