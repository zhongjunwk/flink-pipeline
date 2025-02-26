package com.zj.flink.job;

import cn.hutool.core.util.ObjectUtil;
import com.zj.flink.config.bean.KafkaConfig;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.connector.base.DeliveryGuarantee;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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

        byte[] bytes = ObjectUtil.serialize(configMap);
        String str = new String(bytes, StandardCharsets.ISO_8859_1);
        ConcurrentHashMap<Class<?>, Object> deserializedMap = ObjectUtil.deserialize(str.getBytes(StandardCharsets.ISO_8859_1));
        System.out.println("ConcurrentHashMap deserialized: " + deserializedMap);
    }
}
