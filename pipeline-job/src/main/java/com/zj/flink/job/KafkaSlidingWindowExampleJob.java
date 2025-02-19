package com.zj.flink.job;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.config.bean.annotation.FlinkPipelineConfiguration;
import com.zj.flink.config.util.FlinkConfigUtil;
import com.zj.flink.context.FlinkAnnotationConfigApplicationContext;
import com.zj.flink.context.FlinkApplicationConfigContext;
import com.zj.flink.pipeline.core.PipelineManager;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * Flink Kafka滑动窗口示例作业
 */
public class KafkaSlidingWindowExampleJob {

    /**
     * 主函数
     *
     * @param args 命令行参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        // 获取流执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //设置并行度
        env.setParallelism(1);
        // 使用注解配置类加载 Spring 应用上下文
        AnnotationConfigApplicationContext applicationContext = new FlinkAnnotationConfigApplicationContext(null, FlinkApplicationConfigContext.class).getApplicationContext();
        Map<String, Object> configurationMap = applicationContext.getBeansWithAnnotation(FlinkPipelineConfiguration.class);
        FlinkPipelineConfig flinkConfig = FlinkPipelineConfig.getInstance();
        flinkConfig.initConfig(FlinkConfigUtil.convertSpringBeanConfigurationMapToBeanConfigurationMap(configurationMap));
        new PipelineManager<RecordData>().start(flinkConfig, env);
        // 执行Flink作业
        env.execute("Flink Kafka Sliding Window Example");
    }
}
