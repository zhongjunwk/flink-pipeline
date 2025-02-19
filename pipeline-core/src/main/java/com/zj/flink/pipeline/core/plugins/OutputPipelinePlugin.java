package com.zj.flink.pipeline.core.plugins;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public interface OutputPipelinePlugin<T> extends PipelinePlugin<T> {

    default void process(DataStream<T> dataStream, StreamExecutionEnvironment context) {
        dataStream.print(this.getClass().getName());
    }
}
