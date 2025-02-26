package com.zj.flink.mock.job.sumbmitter;

import com.zj.flink.context.FlinkAnnotationConfigApplicationContext;
import com.zj.flink.context.FlinkApplicationConfigContext;
import com.zj.flink.config.util.FlinkConfigUtil;
import com.zj.flink.config.FlinkPipelineConfiguration;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * Flink Kafka滑动窗口示例数据生产作业
 */
@Slf4j
public class KafkaSlidingWindowExampleDataProducerJobSubmitter {
    /**
     * 主函数
     *
     * @param args 命令行参数
     */
    public static void main(String[] args) {
        // 使用注解配置类加载 Spring 应用上下文
        AnnotationConfigApplicationContext applicationContext = new FlinkAnnotationConfigApplicationContext(null, FlinkApplicationConfigContext.class).getApplicationContext();
        Map<String, FlinkPipelineConfiguration> configurationMap = applicationContext.getBeansOfType(FlinkPipelineConfiguration.class);
        FlinkPipelineConfig flinkConfig = FlinkPipelineConfig.getInstance();
        flinkConfig.initConfig(FlinkConfigUtil.convertSpringBeanConfigurationMapToBeanConfigurationMap(configurationMap));
        log.info("FlinkPipelineConfig deserialized: {}", FlinkPipelineConfig.buildConfigMapSerializableStr());
    }
}
