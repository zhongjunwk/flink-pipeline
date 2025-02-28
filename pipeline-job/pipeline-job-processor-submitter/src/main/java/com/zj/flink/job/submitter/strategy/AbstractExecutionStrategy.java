package com.zj.flink.job.submitter.strategy;

import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.zj.flink.job.submitter.bo.JobStartBO;
import com.zj.flink.job.submitter.bo.JobStatusBO;
import com.zj.flink.job.submitter.bo.JobStopBO;
import lombok.extern.slf4j.Slf4j;

import java.text.MessageFormat;

/**
 * 抽象执行策略
 */
@Slf4j
public abstract class AbstractExecutionStrategy implements ExecutionStrategy {

    public static final String PARAM_FILES = "files";


    @Override
    public String start(JobStartBO req) {
        log.error("{}not support, start error", this.getClass().getName());
        return null;
    }

    @Override
    public Boolean stop(JobStopBO req) {
        log.error("{}not support, stop error", this.getClass().getName());
        return null;
    }

    @Override
    public String status(JobStatusBO req) {
        log.error("{}not support, status error", this.getClass().getName());
        return null;
    }

    /**
     * 获取 jar id
     *
     * @param flinkUrl flink url
     * @param jarName  jar name
     * @return jar id
     */
    protected String getJarId(String flinkUrl, String jarName) {
        String url = MessageFormat.format("{0}jars", flinkUrl);
        String jarId = "";
        try {
            String result = HttpUtil.get(url);
            JSONObject resultJsonObject = new JSONObject(result);
            if (resultJsonObject.containsKey(PARAM_FILES)) {
                JSONArray files = resultJsonObject.getJSONArray(PARAM_FILES);
                for (int i = 0; i < files.size(); i++) {
                    JSONObject file = files.getJSONObject(i);
                    String name = file.getStr("name");
                    if (name.equals(jarName)) {
                        jarId = file.getStr("id");
                        break;
                    }
                }
            }
        } catch (Exception e) {
            log.error(flinkUrl, e);
        }
        return jarId;
    }
}
