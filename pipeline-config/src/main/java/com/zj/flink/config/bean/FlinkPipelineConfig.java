package com.zj.flink.config.bean;

import cn.hutool.core.map.MapUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class FlinkPipelineConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private Map<Class<?>, Object> config = new ConcurrentHashMap<>();

    private FlinkPipelineConfig() {

    }

    public static FlinkPipelineConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public <T> T getConfig(Class<T> type) {
        return (T) config.get(type);
//        return config.values().stream().filter(type::isInstance) // 筛选符合类型的元素
//                .map(type::cast)          // 将匹配的值转换为目标类型
//                .findFirst()              // 返回第一个匹配的值
//                .orElse(null);      // 如果没有匹配项，返回 null，或者抛出异常
    }

    public void initConfig(Map<Class<?>, Object> config) {
        if (MapUtil.isEmpty(config)) {
            return;
        }
        this.config.putAll(config);
    }

    private static class SingletonHolder {
        private static final FlinkPipelineConfig INSTANCE = new FlinkPipelineConfig();
    }
}
