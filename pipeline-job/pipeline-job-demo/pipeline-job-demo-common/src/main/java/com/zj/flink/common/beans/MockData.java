package com.zj.flink.common.beans;

import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
public class MockData implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long rowsPerSecond;
    private Long dataSize;
    private Map<String, List<String>> sensitiveData;
    private List<UserData> userData;
    private List<String> srcIpData;
    private List<String> destIpData;
}