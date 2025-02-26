package com.zj.flink.plugin;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.config.bean.WhiteListConfig;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.plugins.AbstractProcessPipelinePlugin;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@PluginComponent("hostnameFilterPipelinePlugin")
public class HostnameFilterPipelinePlugin extends AbstractProcessPipelinePlugin<RecordData> {

    private static final long serialVersionUID = -1L;

    private WhiteListConfig whiteListConfig;

    @Override
    public void init(FlinkPipelineConfig flinkConfig) {
        this.whiteListConfig = flinkConfig.getConfig(WhiteListConfig.class);
    }

    @Override
    public DataStream<RecordData> process(DataStream<RecordData> dataStream, StreamExecutionEnvironment env) {
        if (null == this.whiteListConfig || CollectionUtils.isEmpty(this.whiteListConfig.getDistIpList())) {
            return dataStream;
        }
        return dataStream.filter(this::filterDestIp);
    }

    private boolean filterDestIp(RecordData recordData) {
        if (StringUtils.isBlank(recordData.getDestIp())) {
            return false;
        }
        return this.whiteListConfig.getDistIpList().contains(String.format("%s:%s", recordData.getDestIp(), recordData.getDestPort()));
    }
}
