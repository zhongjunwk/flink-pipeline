package com.zj.flink.config.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "flink.kafka")
public class SpringKafkaConfig extends KafkaConfig {
}
