package com.zj.flink.mock.config.bean;

import com.zj.flink.common.beans.MockData;
import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;

import java.io.Serializable;


@Data
public class MockConfig implements FlinkPipelineConfiguration, Serializable, Cloneable {
    private static final long serialVersionUID = 1L;
    private MockData mockData;

    @Override
    public MockConfig clone() {
        try {
            return (MockConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public Class<?> getRealSuperClass() {
        return MockConfig.class;
    }
}
