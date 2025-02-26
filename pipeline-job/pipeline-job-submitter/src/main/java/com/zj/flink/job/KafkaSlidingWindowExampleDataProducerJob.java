package com.zj.flink.job;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ObjectUtil;
import com.fasterxml.jackson.core.util.ByteArrayBuilder;
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
import org.apache.flink.connector.base.DeliveryGuarantee;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.nio.cs.US_ASCII;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
        Map<Class<?>, Object> configMap = new ConcurrentHashMap<>();
        KafkaConfig kafkaConfig = new KafkaConfig();
        kafkaConfig.setDeliveryGuarantee(DeliveryGuarantee.AT_LEAST_ONCE.name());
        configMap.put(KafkaConfig.class, kafkaConfig);
        String fileName = "zhongjun.test.ser";
        // 序列化到文件
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            oos.writeObject(configMap);
        }
        String objectString = FileUtil.readString((Paths.get(fileName).toFile()), StandardCharsets.ISO_8859_1);

        FileUtil.writeString(objectString, Paths.get(fileName+1).toFile(), StandardCharsets.ISO_8859_1);
        // 反序列化
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(Paths.get(fileName+1)))) {
            ConcurrentHashMap<Class<?>, Object> deserializedMap = (ConcurrentHashMap<Class<?>, Object>) ois.readObject();
            System.out.println("ConcurrentHashMap deserialized: " + deserializedMap);
        }

        byte[] bytes = ObjectUtil.serialize(configMap);
        String str = new String(bytes, StandardCharsets.ISO_8859_1);
        ConcurrentHashMap<Class<?>, Object> deserializedMap = ObjectUtil.deserialize(str.getBytes(StandardCharsets.ISO_8859_1));
        System.out.println("ConcurrentHashMap deserialized: " + deserializedMap);
    }
}
