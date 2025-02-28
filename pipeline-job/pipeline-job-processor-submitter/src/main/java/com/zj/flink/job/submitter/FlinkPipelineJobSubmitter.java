package com.zj.flink.job.submitter;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.zj.flink.config.FlinkPipelineConfiguration;
import com.zj.flink.config.bean.AppConfig;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.context.FlinkAnnotationConfigApplicationContext;
import com.zj.flink.context.FlinkApplicationConfigContext;
import com.zj.flink.job.FlinkPipelineJob;
import com.zj.flink.job.submitter.bo.FlinkBO;
import com.zj.flink.job.submitter.bo.JobStartBO;
import com.zj.flink.job.submitter.strategy.ExecutionStrategyFacade;
import com.zj.flink.util.FlinkConfigUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * Flink Kafka滑动窗口示例作业
 */
@Slf4j
public class FlinkPipelineJobSubmitter {

    /**
     * 主函数
     *
     * @param args 命令行参数
     * @throws Exception 异常
     */
    public static void main(String[] args) {
        String jobId = ExecutionStrategyFacade.INSTANCE.start(FlinkPipelineJobSubmitter.getJobStartBO());
        if (StrUtil.isBlank(jobId)) {
            log.error("JobId is null");
            return;
        }
        log.info("JobId: {}", jobId);
    }

    private static JobStartBO getJobStartBO() {
        // 使用注解配置类加载 Spring 应用上下文
        AnnotationConfigApplicationContext applicationContext = new FlinkAnnotationConfigApplicationContext(null, FlinkApplicationConfigContext.class).getApplicationContext();
        Map<String, FlinkPipelineConfiguration> configurationMap = applicationContext.getBeansOfType(FlinkPipelineConfiguration.class);
        FlinkPipelineConfig flinkPipelineConfig = FlinkPipelineConfig.getInstance();
        flinkPipelineConfig.initConfig(FlinkConfigUtil.convertSpringBeanConfigurationMapToBeanConfigurationMap(configurationMap));
        AppConfig appConfig = flinkPipelineConfig.getConfig(AppConfig.class);
        if (null == appConfig) {
            log.error("Flink config is null");
            throw new RuntimeException("Flink config is null");
        }
        JobStartBO jobStartBO = new JobStartBO();
        FlinkBO flinkBO = new FlinkBO();
        AppConfig.FlinkConfig flinkConfig = appConfig.getFlinkConfig();
        if (null == flinkConfig) {
            log.error("Flink config is null");
            throw new RuntimeException("Flink config is null");
        }
        if (StrUtil.isBlank(flinkConfig.getUrl())) {
            log.error("Flink url is null");
            throw new RuntimeException("Flink url is null");
        }
        flinkBO.setUrl(flinkConfig.getUrl());
        if (StrUtil.isBlank(flinkConfig.getMode())) {
            log.error("Flink mode is null");
            throw new RuntimeException("Flink mode is null");
        }
        flinkBO.setMode(flinkConfig.getMode());
        jobStartBO.setFlinkBO(flinkBO);
        if (StrUtil.isBlank(flinkConfig.getJarName())) {
            log.error("Flink jarName is null");
            throw new RuntimeException("Flink jarName is null");
        }
        jobStartBO.setJarName(flinkConfig.getJarName());
        if (null != flinkConfig.getParallelism()) {
            jobStartBO.setParallelism(flinkConfig.getParallelism());
        }
        if (!StrUtil.isBlank(flinkConfig.getSavePointPath())) {
            jobStartBO.setSavePointPath(flinkConfig.getSavePointPath());
        }
        if (null != flinkConfig.getAllowNonRestoredState()) {
            jobStartBO.setAllowNonRestoredState(flinkConfig.getAllowNonRestoredState());
        }
        jobStartBO.setEntryClass(FlinkPipelineJob.class.getName());
        if (StrUtil.isNotBlank(flinkConfig.getEntryClass())) {
            jobStartBO.setEntryClass(flinkConfig.getEntryClass().trim());
        }
        jobStartBO.setArguments(FlinkPipelineConfig.buildConfigMapSerializableStr());
        log.info("FlinkPipelineJobSubmitter.getJobStartBO:{}", JSONUtil.toJsonStr(jobStartBO));
        return jobStartBO;
    }
}
