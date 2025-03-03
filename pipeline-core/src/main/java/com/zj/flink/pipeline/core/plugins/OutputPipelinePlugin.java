package com.zj.flink.pipeline.core.plugins;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public interface OutputPipelinePlugin<T> extends PipelinePlugin<T> {

    void process(DataStream<T> dataStream, StreamExecutionEnvironment context);

    default void process(DataStream<T> dataStream, StreamExecutionEnvironment context, boolean disableChaining) {
        if (dataStream == null) {
            return;
        }
        if (disableChaining) {
            dataStream.print(this.getClass().getName()).name(this.getClass().getName()).disableChaining();
        } else {
            dataStream.print(this.getClass().getName());
        }
    }
}
