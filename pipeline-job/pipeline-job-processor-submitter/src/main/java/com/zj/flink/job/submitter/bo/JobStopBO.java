package com.zj.flink.job.submitter.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 停止任务请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JobStopBO {
    /**
     * yarn id, model 为 yarn 时必传
     */
    private String yid;

    private String jobId;

    /**
     * Flink 相关配置
     */
    private FlinkBO flinkBO;
}
