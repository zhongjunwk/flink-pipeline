package com.zj.flink.job.submitter.bo;

import lombok.Data;

/**
 * 获取 Job 状态
 */
@Data
public class JobStatusBO {
    private String jobId;

    /**
     * Flink 相关配置
     */
    private FlinkBO flinkBO;

}
