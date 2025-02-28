package com.zj.flink.common.beans;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.zj.flink.pipeline.core.bean.PipelineRecord;
import com.zj.flink.risk.beans.RiskStrategy;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RecordData extends PipelineRecord {

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
