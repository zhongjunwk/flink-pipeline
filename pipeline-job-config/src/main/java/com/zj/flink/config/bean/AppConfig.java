package com.zj.flink.config.bean;

import com.zj.flink.config.bean.annotation.FlinkConfiguration;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Data
@Configuration
@ConfigurationProperties(prefix = "flink")
@FlinkConfiguration("appConfig")
public class AppConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private String appName;
}
