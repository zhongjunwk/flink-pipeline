package com.zj.flink.common.beans;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.zj.flink.risk.beans.RiskStrategy;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private LocalDateTime reqTime;
    private String protocol;
    private String hostname;
    private String srcIp;
    private String srcPort;
    private String destIp;
    private String destPort;
    private String body;
    private Set<RiskStrategy> riskStrategySet = new ConcurrentHashSet<>();
}
