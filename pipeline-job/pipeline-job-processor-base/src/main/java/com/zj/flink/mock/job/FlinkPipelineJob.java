package com.zj.flink.mock.job;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.config.bean.FlinkPipelineConfig;
import com.zj.flink.config.bean.PipelineConfig;
import com.zj.flink.pipeline.core.PipelineManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

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
        PipelineConfig pipelineConfig = FlinkPipelineConfig.getInstance().getConfig(PipelineConfig.class);
        //设置并行度
        env.setParallelism(pipelineConfig.getParallelism());
        new PipelineManager<RecordData>().start(FlinkPipelineConfig.getInstance(), env);
        // 执行Flink作业
        env.execute(pipelineConfig.getPipelineName());
    }
}
