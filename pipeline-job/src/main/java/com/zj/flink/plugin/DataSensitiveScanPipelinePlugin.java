package com.zj.flink.plugin;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.config.bean.SensitiveDataConfig;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.plugins.AbstractProcessPipelinePlugin;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@PluginComponent("dataSensitiveScanPipelinePlugin")
public class DataSensitiveScanPipelinePlugin extends AbstractProcessPipelinePlugin<RecordData> {

    private SensitiveDataConfig censitiveDataConfig;

    @Override
    public void init(FlinkPipelineConfig flinkConfig) {
        this.censitiveDataConfig = flinkConfig.getConfig(SensitiveDataConfig.class);
    }

    @Override
    public DataStream<RecordData> process(DataStream<RecordData> dataStream, StreamExecutionEnvironment env) {
        if (null == this.censitiveDataConfig || CollectionUtils.isEmpty(this.censitiveDataConfig.getSensitiveDataList())) {
            return dataStream;
        }
        return dataStream.filter(this::hasSensitiveData);
    }

    private boolean hasSensitiveData(RecordData recordData) {
        if (StringUtils.isBlank(recordData.getBody())) {
            return false;
        }
        for (String sensitiveData : this.censitiveDataConfig.getSensitiveDataList()) {
            if (recordData.getBody().contains(sensitiveData)) {
                return true;
            }
        }
        return false;
    }
}
