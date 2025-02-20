package com.zj.flink.common.beans;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class IpInfoData implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ip;
    private String port;
    private String mac;
}
