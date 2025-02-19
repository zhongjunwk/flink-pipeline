package com.zj.flink.context;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

@Configuration
@EnableAutoConfiguration
@EnableConfigurationProperties
@ConfigurationPropertiesScan(basePackages = {"com.zj"})
@ComponentScans({@ComponentScan("com.zj")})
public class FlinkApplicationConfigContext implements Serializable {
    private static final long serialVersionUID = 1L;
}
