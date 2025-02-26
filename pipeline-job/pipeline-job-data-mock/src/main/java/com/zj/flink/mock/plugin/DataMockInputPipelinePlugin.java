package com.zj.flink.mock.plugin;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.mock.config.bean.MockConfig;
import com.zj.flink.mock.utils.MockDataUtil;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.plugins.AbstractInputPipelinePlugin;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.apache.flink.streaming.api.functions.source.datagen.RandomGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Slf4j
@PluginComponent("dataMockInputPipelinePlugin")
public class DataMockInputPipelinePlugin extends AbstractInputPipelinePlugin<RecordData> {

    private MockConfig mockConfig;

    @Override
    public void init(FlinkPipelineConfig flinkConfig) {
        this.mockConfig = flinkConfig.getConfig(MockConfig.class);
    }

    @Override
    public DataStream<RecordData> process(StreamExecutionEnvironment env) {
        List<RecordData> dataDemoList = MockDataUtil.initDataDemo(mockConfig.getMockData(), mockConfig.getMockData().getSensitiveData(), mockConfig.getMockData().getUserData(), mockConfig.getMockData().getSrcIpData(), mockConfig.getMockData().getDestIpData());
        DataGeneratorSource<RecordData> generatorSource = new DataGeneratorSource<>(new RandomGenerator<RecordData>() {
            @Override
            public RecordData next() {
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                RecordData randomRecordData = dataDemoList.get(new Random().nextInt(dataDemoList.size()));
                randomRecordData.setId(UUID.randomUUID().toString());
                randomRecordData.setReqTime(LocalDateTime.now());
                return randomRecordData;
            }
        }, mockConfig.getMockData().getRowsPerSecond(), null);
        return env.addSource(generatorSource).returns(RecordData.class);
    }
}
