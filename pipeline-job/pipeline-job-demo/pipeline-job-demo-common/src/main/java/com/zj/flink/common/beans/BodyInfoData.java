package com.zj.flink.common.beans;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class BodyInfoData implements Serializable {

    private static final long serialVersionUID = 1L;

    private UserInfoData userInfo;

    private Map<String, List<String>> sensitiveDataMap;
}
