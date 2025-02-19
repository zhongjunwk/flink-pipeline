package com.zj.flink.config.bean;

import com.zj.flink.config.bean.annotation.FlinkConfiguration;
import com.zj.flink.risk.beans.RiskStrategy;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@FlinkConfiguration("riskStrategyConfig")
public class RiskStrategyConfig implements Serializable, Cloneable {
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
}
