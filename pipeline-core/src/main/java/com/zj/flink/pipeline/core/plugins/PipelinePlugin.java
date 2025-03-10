package com.zj.flink.pipeline.core.plugins;

import com.zj.flink.config.bean.FlinkPipelineConfig;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;

import java.io.Serializable;

public interface PipelinePlugin<T> extends Serializable {

    String getName();

    void setName(String name);

    void init(FlinkPipelineConfig flinkConfig);

    default void disableChaining(DataStream<T> dataStream, boolean disableChaining) {
        if (dataStream == null) {
            return;
        }
        if (disableChaining && dataStream instanceof SingleOutputStreamOperator) {
            ((SingleOutputStreamOperator<T>) dataStream).name(this.getClass().getSimpleName());
            ((SingleOutputStreamOperator<T>) dataStream).disableChaining();
        }
    }
}
