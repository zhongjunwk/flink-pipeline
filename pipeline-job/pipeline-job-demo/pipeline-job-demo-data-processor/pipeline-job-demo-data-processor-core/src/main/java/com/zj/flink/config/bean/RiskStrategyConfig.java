package com.zj.flink.config.bean;

import com.zj.flink.config.FlinkPipelineConfiguration;
import com.zj.flink.risk.beans.RiskStrategy;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RiskStrategyConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private List<RiskStrategy> riskStrategyList;

    @Override
    public RiskStrategyConfig clone() {
        try {
            return (RiskStrategyConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return RiskStrategyConfig.class;
    }
}
