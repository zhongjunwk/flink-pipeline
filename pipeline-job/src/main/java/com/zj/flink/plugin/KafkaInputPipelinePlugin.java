package com.zj.flink.plugin;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.common.deserialization.JsonDataDeserializationSchema;
import com.zj.flink.config.bean.FlinkConfig;
import com.zj.flink.config.bean.KafkaConfig;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.plugins.AbstractInputPipelinePlugin;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@PluginComponent("kafkaInputPipelinePlugin")
public class KafkaInputPipelinePlugin extends AbstractInputPipelinePlugin<RecordData> {

    private KafkaConfig kafkaConfig;

    @Override
    public void init(FlinkConfig flinkConfig) {
        this.kafkaConfig = flinkConfig.getConfig(KafkaConfig.class);
    }

    @Override
    public DataStream<RecordData> process(StreamExecutionEnvironment env) {
        DataStream<RecordData> dataStream = env.fromSource(KafkaSource.<RecordData>builder().setProperties(kafkaConfig.getConsumer())
                // Kafka 服务器地址
                .setBootstrapServers(kafkaConfig.getConsumerServers())
                // 订阅的 Kafka 主题
                .setTopics(kafkaConfig.getConsumerTopic())
                // 消费者组 ID2
                .setGroupId(kafkaConfig.getConsumerGroupId())
                // 从最早的偏移量开始读取
                .setStartingOffsets(OffsetsInitializer.latest())
                // 反序列化器，仅提取消息的值部分
                .setDeserializer(JsonDataDeserializationSchema.of(RecordData.class))
                .build(), WatermarkStrategy.forMonotonousTimestamps(), this.getClass().getName());
        return dataStream;
    }
}
