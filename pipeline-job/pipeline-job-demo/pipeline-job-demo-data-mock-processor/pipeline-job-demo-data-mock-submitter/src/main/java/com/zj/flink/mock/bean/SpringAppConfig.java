package com.zj.flink.mock.bean;

import com.zj.flink.config.bean.AppConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "flink.app")
public class SpringAppConfig extends AppConfig {
}
