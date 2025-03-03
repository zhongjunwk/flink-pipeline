package com.zj.flink.pipeline.core.plugins;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public interface ProcessPipelinePlugin<T> extends PipelinePlugin<T> {

    DataStream<T> process(DataStream<T> dataStream, StreamExecutionEnvironment context);

    default DataStream<T> process(DataStream<T> dataStream, StreamExecutionEnvironment context, boolean disableChaining) {
        DataStream<T> dataStreamNext = this.process(dataStream, context);
        if (dataStreamNext == null) {
            return dataStreamNext;
        }
        if (disableChaining && dataStreamNext instanceof SingleOutputStreamOperator) {
            ((SingleOutputStreamOperator<T>) dataStreamNext).name(this.getClass().getSimpleName());
            ((SingleOutputStreamOperator<T>) dataStreamNext).disableChaining();
        }
        return dataStreamNext;
    }
}