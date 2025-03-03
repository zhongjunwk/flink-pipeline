package com.zj.flink.pipeline.core.plugins;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public abstract class AbstractOutputPipelinePlugin<T> extends AbstractPipelinePlugin<T> implements OutputPipelinePlugin<T> {

    public void process(DataStream<T> dataStream, StreamExecutionEnvironment context) {
        if (dataStream == null) {
            return;
        }
        dataStream.print(this.getClass().getName()).name(this.getClass().getName());
    }
}
