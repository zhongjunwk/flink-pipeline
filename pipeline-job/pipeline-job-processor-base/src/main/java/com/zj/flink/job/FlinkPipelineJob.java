package com.zj.flink.job;

import com.zj.flink.config.bean.AppConfig;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.pipeline.core.PipelineManager;
import com.zj.flink.pipeline.core.bean.PipelineRecord;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.restartstrategy.RestartStrategies;
import org.apache.flink.api.common.time.Time;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

import java.util.concurrent.TimeUnit;

/**
 * Flink Kafka滑动窗口示例数据生产作业
 */
@Slf4j
public class FlinkPipelineJob {
    /**
     * 主函数
     *
     * @param args 命令行参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception {
        FlinkPipelineConfig.initConfig(args[0]);
        // 获取流执行环境
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        AppConfig appConfig = FlinkPipelineConfig.getInstance().getConfig(AppConfig.class);
        if (null == appConfig) {
            log.error("FlinkPipelineConfig is null");
            return;
        }
        if (null == appConfig.getFlinkConfig()) {
            log.error("FlinkPipelineConfig.FlinkConfig is null");
            return;
        }
        int restartAttempts = null == appConfig.getFlinkConfig().getRestartAttempts() ? Integer.MAX_VALUE : appConfig.getFlinkConfig().getRestartAttempts();
        int delayIntervalSeconds = null == appConfig.getFlinkConfig().getDelayIntervalSeconds() ? 10 : appConfig.getFlinkConfig().getDelayIntervalSeconds();
        env.setRestartStrategy(RestartStrategies.fixedDelayRestart(restartAttempts, Time.of(delayIntervalSeconds, TimeUnit.SECONDS)));
        //设置并行度
        new PipelineManager<PipelineRecord>().start(FlinkPipelineConfig.getInstance(), env);
        // 执行Flink作业
        env.execute(appConfig.getAppName());
    }
}
