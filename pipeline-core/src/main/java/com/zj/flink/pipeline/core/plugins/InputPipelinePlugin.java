package com.zj.flink.pipeline.core.plugins;

import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

public interface InputPipelinePlugin<T> extends PipelinePlugin<T> {

    DataStream<T> process(StreamExecutionEnvironment context);

    default DataStream<T> process(StreamExecutionEnvironment context, boolean disableChaining) {
        DataStream<T> dataStreamNext = this.process(context);
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
