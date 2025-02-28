package com.zj.flink.job.submitter.bo;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 启动任务请求
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class JobStartBO {

    /**
     * Flink 相关配置
     */
    private FlinkBO flinkBO;


    /**
     * jar 名称
     */
    private String jarName;

    /**
     * 并行度
     */
    private Integer parallelism = 1;

    /**
     * jar 主类
     */
    private String entryClass;


    private String savePointPath;


    private Boolean allowNonRestoredState = false;

    /**
     * 规则串数据
     */
    private String arguments;
}
