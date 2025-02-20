package com.zj.flink.config.bean;

import com.zj.flink.pipeline.core.config.bean.PipelineConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "flink.pipeline")
public class SpringPipelineConfig extends PipelineConfig {
}
