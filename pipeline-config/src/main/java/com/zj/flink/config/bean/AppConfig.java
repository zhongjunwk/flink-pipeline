package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;

@Data
public class AppConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {

    private String appName;

    private FlinkConfig flinkConfig;

    @Override
    public AppConfig clone() {
        try {
            return (AppConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return AppConfig.class;
    }

    @Data
    public static class FlinkConfig implements Serializable, Cloneable {

        /**
         * 运行模式
         */
        private String mode;

        /**
         * Flink 提交地址
         */
        private String url;

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

        /**
         * savepoint 路径
         */
        private String savePointPath;

        /**
         * 是否允许非恢复状态
         */
        private Boolean allowNonRestoredState = false;


        @Override
        public FlinkConfig clone() {
            try {
                return (FlinkConfig) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new AssertionError();
            }
        }
    }
}