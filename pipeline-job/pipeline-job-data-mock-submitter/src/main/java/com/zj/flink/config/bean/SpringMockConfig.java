package com.zj.flink.config.bean;

import com.zj.flink.mock.config.bean.MockConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "flink.mock")
public class SpringMockConfig extends MockConfig {
}