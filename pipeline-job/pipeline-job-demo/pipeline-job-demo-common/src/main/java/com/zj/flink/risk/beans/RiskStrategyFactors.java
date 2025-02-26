package com.zj.flink.risk.beans;

import lombok.Data;

import java.io.Serializable;

@Data
public class RiskStrategyFactors implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long limitNum;
}