package com.zj.flink.risk.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RiskStrategy implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String type;
    private String discription;
    private List<RiskStrategyFactors> riskStrategyFactorsList;
}