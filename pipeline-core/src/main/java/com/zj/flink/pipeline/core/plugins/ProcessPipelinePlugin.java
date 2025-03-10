package com.zj.flink.pipeline.core.plugins;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public interface ProcessPipelinePlugin<T> extends PipelinePlugin<T> {

    DataStream<T> process(DataStream<T> dataStream, StreamExecutionEnvironment context);

    default DataStream<T> process(DataStream<T> dataStream, StreamExecutionEnvironment context, boolean disableChaining) {
        DataStream<T> dataStreamNext = this.process(dataStream, context);
        PipelinePlugin.super.disableChaining(dataStreamNext, disableChaining);
        return dataStreamNext;
    }
}