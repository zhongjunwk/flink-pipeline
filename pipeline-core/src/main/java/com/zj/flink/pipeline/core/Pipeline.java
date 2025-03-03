package com.zj.flink.pipeline.core;

import com.zj.flink.pipeline.core.plugins.InputPipelinePlugin;
import com.zj.flink.pipeline.core.plugins.OutputPipelinePlugin;
import com.zj.flink.pipeline.core.plugins.PipelinePlugin;
import com.zj.flink.pipeline.core.plugins.ProcessPipelinePlugin;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections.CollectionUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public final class Pipeline<T> {

    private final Logger logger = LoggerFactory.getLogger(Pipeline.class);

    @Setter
    @Getter
    private String name;

    @Setter
    @Getter
    private PipelinePlugin<T> inputPlugin;

    @Setter
    @Getter
    private List<PipelinePlugin<T>> processPipelinePluginList;

    @Setter
    @Getter
    private PipelinePlugin<T> outputPlugin;

    public void start(StreamExecutionEnvironment env, boolean disableChaining) {
        if (null == inputPlugin) {
            logger.warn("input plugin is null");
            return;
        }
        if (!(inputPlugin instanceof InputPipelinePlugin)) {
            logger.warn("input plugin is not InputPipelinePlugin");
            return;
        }
        DataStream<T> dataStream = this.process(((InputPipelinePlugin<T>) inputPlugin).process(env, disableChaining), env, disableChaining);
        if (null == outputPlugin) {
            logger.warn("output plugin is null");
            return;
        }
        if (!(outputPlugin instanceof OutputPipelinePlugin)) {
            logger.warn("output plugin is not OutputPipelinePlugin");
            return;
        }
        ((OutputPipelinePlugin<T>) outputPlugin).process(dataStream, env, disableChaining);
    }

    private DataStream<T> process(DataStream<T> dataStream, StreamExecutionEnvironment env, boolean disableChaining) {
        if (CollectionUtils.isEmpty(processPipelinePluginList)) {
            logger.warn("process pipeline plugin list is empty");
            return dataStream;
        }
        DataStream<T> dataStreamNext = dataStream;
        for (PipelinePlugin<T> pipelinePlugin : processPipelinePluginList) {
            dataStreamNext = ((ProcessPipelinePlugin<T>) pipelinePlugin).process(dataStreamNext, env, disableChaining);
        }
        return dataStreamNext;
    }
}
