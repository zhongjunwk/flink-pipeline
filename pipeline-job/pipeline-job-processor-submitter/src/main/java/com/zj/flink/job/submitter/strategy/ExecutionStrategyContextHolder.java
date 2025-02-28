package com.zj.flink.job.submitter.strategy;

import com.zj.flink.job.submitter.bo.JobStartBO;
import com.zj.flink.job.submitter.bo.JobStatusBO;
import com.zj.flink.job.submitter.bo.JobStopBO;

public class ExecutionStrategyContextHolder {
    private static ExecutionStrategy context;

    public ExecutionStrategyContextHolder(String mode) {
        context = ExecutionModeEnum.getInstance(mode).getExecutionStrategy();
    }

    public String start(JobStartBO req) {
        return context.start(req);
    }

    /**
     * 停止 jar
     *
     * @param req
     * @return
     */
    public Boolean stop(JobStopBO req) {
        return context.stop(req);
    }

    /**
     * 状态查询
     *
     * @param req
     * @return
     */
    public String status(JobStatusBO req) {
        return context.status(req);
    }
}
