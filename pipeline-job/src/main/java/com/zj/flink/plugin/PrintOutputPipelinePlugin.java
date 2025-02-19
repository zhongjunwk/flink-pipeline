package com.zj.flink.plugin;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.plugins.AbstractOutputPipelinePlugin;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@PluginComponent("printOutputPipelinePlugin")
public class PrintOutputPipelinePlugin extends AbstractOutputPipelinePlugin<RecordData> {

    @Override
    public void init(FlinkPipelineConfig flinkConfig) {
    }

    @Override
    public void process(DataStream<RecordData> dataStream, StreamExecutionEnvironment context) {
        super.process(dataStream, context);
    }
}
