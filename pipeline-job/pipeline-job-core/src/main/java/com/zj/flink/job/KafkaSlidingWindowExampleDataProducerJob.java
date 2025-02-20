package com.zj.flink.job;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.common.serialization.JsonDataSerializationSchema;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.config.bean.KafkaConfig;
import com.zj.flink.config.bean.annotation.FlinkPipelineConfiguration;
import com.zj.flink.config.util.FlinkConfigUtil;
import com.zj.flink.context.FlinkAnnotationConfigApplicationContext;
import com.zj.flink.context.FlinkApplicationConfigContext;
import com.zj.flink.mock.service.MockDataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * Flink Kafka滑动窗口示例数据生产作业
 */
@Slf4j
public class KafkaSlidingWindowExampleDataProducerJob {
    /**
     * 主函数
     *
     * @param args 命令行参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置并行度
        env.setParallelism(1);
        // 使用注解配置类加载 Spring 应用上下文
        AnnotationConfigApplicationContext applicationContext = new FlinkAnnotationConfigApplicationContext(null, FlinkApplicationConfigContext.class).getApplicationContext();
        Map<String, Object> configurationMap = applicationContext.getBeansWithAnnotation(FlinkPipelineConfiguration.class);
        FlinkPipelineConfig flinkConfig = FlinkPipelineConfig.getInstance();
        flinkConfig.initConfig(FlinkConfigUtil.convertSpringBeanConfigurationMapToBeanConfigurationMap(configurationMap));
        MockDataService mockDataService = applicationContext.getBean(MockDataService.class);
        DataGeneratorSource<RecordData> generatorSource = mockDataService.getRecordDataDataGeneratorSource();
        DataStreamSource<RecordData> inputDataStream = (DataStreamSource<RecordData>) env.addSource(generatorSource).returns(RecordData.class);
        // 设置Kafka连接属性
        KafkaConfig kafkaConfig = flinkConfig.getConfig(KafkaConfig.class);
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
        inputDataStream.sinkTo(kafkaSink);
        inputDataStream.print("inputDataStream");
        // 执行Flink作业
        env.execute("Flink Kafka Sliding Window Example Data Job");
    }
}
