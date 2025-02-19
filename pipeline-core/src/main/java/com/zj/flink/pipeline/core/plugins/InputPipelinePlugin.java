package com.zj.flink.pipeline.core.plugins;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public interface InputPipelinePlugin<T> extends PipelinePlugin<T> {

    DataStream<T> process(StreamExecutionEnvironment context);
}
