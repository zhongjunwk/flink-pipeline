package com.zj.flink.mock.config;

import com.zj.flink.common.beans.MockData;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;


@Data
@Configuration
@ConfigurationProperties(prefix = "flink.mock")
public class MockConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private MockData mockData;

}
