package com.zj.flink.job.submitter.strategy;

import com.zj.flink.job.submitter.strategy.impl.StandAloneExecutionStrategy;
import com.zj.flink.job.submitter.strategy.impl.YarnExecutionStrategy;
import com.zj.flink.job.submitter.strategy.impl.YarnSessionExecutionStrategy;
import lombok.Getter;

/**
 * 运行模式枚举
 */
@Getter
public enum ExecutionModeEnum {
    STAND_ALONE("standalone", "standalone 模式", new StandAloneExecutionStrategy()),
    YARN("yarn", "yarn 模式", new YarnExecutionStrategy()),
    YARN_SESSION("yarn-session", "yarn session 模式", new YarnSessionExecutionStrategy()),
    ;

    /**
     * 运行模式
     */
    private String mode;

    /**
     * 模式描述
     */
    private String desc;

    /**
     * 执行策略
     */
    private ExecutionStrategy executionStrategy;


    ExecutionModeEnum(String mode, String desc, ExecutionStrategy executionStrategy) {
        this.mode = mode;
        this.desc = desc;
        this.executionStrategy = executionStrategy;
    }

    public static ExecutionModeEnum getInstance(String mode) {
        for (ExecutionModeEnum executionModeEnum : ExecutionModeEnum.values()) {
            if (executionModeEnum.mode.equalsIgnoreCase(mode)) {
                return executionModeEnum;
            }
        }
        throw new IllegalArgumentException("unknown execution mode : " + mode);
    }

    /**
     * 是否包含输入的模式
     *
     * @param model
     * @return
     */
    public static Boolean contains(String model) {
        Boolean result = Boolean.FALSE;
        for (ExecutionModeEnum executionModeEnum : ExecutionModeEnum.values()) {
            if (executionModeEnum.getMode().equals(model)) {
                result = Boolean.TRUE;
                break;
            }
        }
        return result;
    }
}
