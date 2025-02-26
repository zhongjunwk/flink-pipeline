package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class WhiteListConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private List<String> distIpList;

    @Override
    public WhiteListConfig clone() {
        try {
            return (WhiteListConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return WhiteListConfig.class;
    }
}
