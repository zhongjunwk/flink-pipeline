package com.zj.flink.config.bean;

import com.zj.flink.config.bean.annotation.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@FlinkPipelineConfiguration("whiteListConfig")
public class WhiteListConfig implements Serializable, Cloneable {
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
}
