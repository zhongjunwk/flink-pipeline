package com.zj.flink.job.submitter.strategy.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import com.zj.flink.job.submitter.bo.FlinkBO;
import com.zj.flink.job.submitter.bo.JobStartBO;
import com.zj.flink.job.submitter.bo.JobStatusBO;
import com.zj.flink.job.submitter.bo.JobStopBO;
import com.zj.flink.job.submitter.strategy.AbstractExecutionStrategy;
import com.zj.flink.job.submitter.strategy.ExecutionStrategy;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * standalone 模式执行策略
 */
@Slf4j
public class StandAloneExecutionStrategy extends AbstractExecutionStrategy implements ExecutionStrategy {

    @Override
    public String start(JobStartBO jobStartBO) {
        String jobId = "";
        FlinkBO flinkBo = jobStartBO.getFlinkBO();
        // 规则串编码
        String encodeParams = jobStartBO.getArguments();
        //接口获取规则引擎jar包id
        String jar = getJarId(flinkBo.getUrl(), jobStartBO.getJarName());
        if (StrUtil.isBlank(jar)) {
            log.error("执行任务失败，jar:{}不存在.", jobStartBO.getJarName());
            return jobId;
        }
        String savePointPath = "";
        if (StrUtil.isNotBlank(jobStartBO.getSavePointPath())) {
            savePointPath = "&savepointPath=" + jobStartBO.getSavePointPath();
        }
        String param = "allowNonRestoredState=" + jobStartBO.getAllowNonRestoredState() + "&entry-class=" + jobStartBO.getEntryClass() + "&parallelism=" + jobStartBO.getParallelism() + "&program-args=" + encodeParams + savePointPath;
        String url = String.format ("%sjars/%s/run?%s", flinkBo.getUrl(), jar, param);
        log.info("向flink集群提交任务.url:{}", url);

        String result = HttpUtil.post(url, "");
        if (StrUtil.isBlank(result)) {
            log.error("提交flink任务失败，接口无返回值.");
            return jobId;
        }
        JSONObject resultJsonObject = new JSONObject(result);
        if (!resultJsonObject.containsKey("jobid")) {
            log.error("提交flink任务失败.失败信息：{}", result);
            return jobId;
        }
        jobId = resultJsonObject.getStr("jobid");
        if (StrUtil.isBlank(jobId)) {
            log.error("提交flink任务失败.失败信息：{}", result);
            return jobId;
        }
        return jobId;
    }

    @Override
    public Boolean stop(JobStopBO jobStopBO) {
        Boolean status = Boolean.FALSE;
        String url = MessageFormat.format("{0}jobs/{1}/yarn-cancel", jobStopBO.getFlinkBO().getUrl(), jobStopBO.getJobId());

        log.debug("向flink集群发送的URL请求:{}", url);
        String result = HttpUtil.get(url);
        log.debug("standalone模式关闭任务成功，并返回结果：{}", result);
        if (StrUtil.isNotBlank(result)) {
            status = Boolean.TRUE;
        }
        return status;
    }

    @Override
    public String status(JobStatusBO jobStatusBO) {
        String status = "NOTFOUND";
        String url = MessageFormat.format("{0}jobs/{1}", jobStatusBO.getFlinkBO().getUrl(), jobStatusBO.getJobId());
        log.info("实时策略发送的Flink集群查询任务状态URL请求:{}", url);
        String result = HttpUtil.get(url);
        if (StrUtil.isNotBlank(result)) {
            JSONObject json = new JSONObject(result);
            if (json.containsKey("state")) {
                status = json.getStr("state");
            }
        }
        return status;
    }
}
