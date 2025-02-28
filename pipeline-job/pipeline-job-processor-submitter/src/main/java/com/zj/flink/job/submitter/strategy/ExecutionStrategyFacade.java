package com.zj.flink.job.submitter.strategy;

import com.zj.flink.job.submitter.bo.JobStartBO;
import com.zj.flink.job.submitter.bo.JobStatusBO;
import com.zj.flink.job.submitter.bo.JobStopBO;

/**
 * 执行策略门面
 */
public class ExecutionStrategyFacade {

    public static final ExecutionStrategyFacade INSTANCE = new ExecutionStrategyFacade();

    public String start(JobStartBO jobStartBO) {

        return new ExecutionStrategyContextHolder(jobStartBO.getFlinkBO().getMode()).start(jobStartBO);
    }

    public Boolean stop(JobStopBO jobStopBO) {
        return new ExecutionStrategyContextHolder(jobStopBO.getFlinkBO().getMode()).stop(jobStopBO);
    }

    public String status(JobStatusBO jobStatusBO) {
        return new ExecutionStrategyContextHolder(jobStatusBO.getFlinkBO().getMode()).status(jobStatusBO);
    }
}
