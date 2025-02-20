package com.zj.flink.config.bean;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@EqualsAndHashCode(callSuper = true)
@Data
@Configuration
@ConfigurationProperties(prefix = "flink.risk-strategy")
public class SpringRiskStrategyConfig extends RiskStrategyConfig {
}
