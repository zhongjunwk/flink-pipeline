package com.zj.flink.job.submitter.strategy;

import com.zj.flink.job.submitter.bo.JobStartBO;
import com.zj.flink.job.submitter.bo.JobStatusBO;
import com.zj.flink.job.submitter.bo.JobStopBO;

/**
 * 运行模式策略接口
 */
public interface ExecutionStrategy {

    /**
     * 运行 jar
     *
     * @param req req
     * @return
     */
    String start(JobStartBO req);

    /**
     * 停止 jar
     *
     * @param req req
     * @return
     */
    Boolean stop(JobStopBO req);

    /**
     * 状态查询
     *
     * @param req req
     * @return
     */
    String status(JobStatusBO req);
}
