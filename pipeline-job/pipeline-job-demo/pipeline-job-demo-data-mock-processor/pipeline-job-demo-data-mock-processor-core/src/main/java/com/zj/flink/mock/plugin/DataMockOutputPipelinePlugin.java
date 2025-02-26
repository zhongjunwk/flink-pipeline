package com.zj.flink.mock.plugin;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.common.serialization.JsonDataSerializationSchema;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.mock.config.bean.KafkaConfig;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.plugins.AbstractOutputPipelinePlugin;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

@PluginComponent("dataMockOutputPipelinePlugin")
public class DataMockOutputPipelinePlugin extends AbstractOutputPipelinePlugin<RecordData> {

    private KafkaConfig kafkaConfig;

    @Override
    public void init(FlinkPipelineConfig flinkConfig) {
        this.kafkaConfig = flinkConfig.getConfig(KafkaConfig.class);
    }

    @Override
    public void process(DataStream<RecordData> dataStream, StreamExecutionEnvironment context) {
        KafkaSink<RecordData> kafkaSink = KafkaSink.<RecordData>builder()
                .setKafkaProducerConfig(kafkaConfig.getProducer())
                .setBootstrapServers(kafkaConfig.getProducerServers())
                .setRecordSerializer(KafkaRecordSerializationSchema
                        .<RecordData>builder()
                        .setTopic(kafkaConfig.getProducerTopic())
                        .setValueSerializationSchema(new JsonDataSerializationSchema<>())
                        .build())
                .setDeliveryGuarantee(kafkaConfig.getDeliveryGuarantee())
                .build();
        dataStream.sinkTo(kafkaSink);
    }
}
