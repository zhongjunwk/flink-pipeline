package com.zj.flink.job.submitter.bo;

import com.zj.flink.job.submitter.strategy.ExecutionModeEnum;
import lombok.Data;

/**
 * flink 提交参数
 */
@Data
public class FlinkBO {

    /**
     * @see ExecutionModeEnum
     */
    private String mode;

    /**
     * Flink 提交地址
     */
    private String url;


}
