package com.zj.flink.config.bean;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.zj.flink.config.FlinkPipelineConfiguration;
import com.zj.flink.config.util.FstHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@Slf4j
public class FlinkPipelineConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Charset DEFAULT_CHARSET = StandardCharsets.ISO_8859_1;
    private Map<Class<?>, FlinkPipelineConfiguration> config = new ConcurrentHashMap<>();

    // 私有构造函数，防止外部实例化
    private FlinkPipelineConfig() {
        // 构造函数私有化，确保单例模式
    }

    public static FlinkPipelineConfig getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public static void initConfig(String configMapSerializableStr) {
        FlinkPipelineConfig.getInstance().initConfig(FlinkPipelineConfig.buildConfigMap(configMapSerializableStr));
    }

    public static Map<Class<?>, FlinkPipelineConfiguration> buildConfigMap(String configMapSerializableStr) {
        if (StrUtil.isBlank(configMapSerializableStr)) {
            return new ConcurrentHashMap<>();
        }
        return FstHelper.string2Obj(configMapSerializableStr);
    }

    public static String buildConfigMapSerializableStr(Map<Class<?>, FlinkPipelineConfiguration> configMap) {
        if (MapUtil.isEmpty(configMap)) {
            return "";
        }
        return FstHelper.obj2String(configMap);
    }

    public static String buildConfigMapSerializableStr() {
        return FlinkPipelineConfig.buildConfigMapSerializableStr(FlinkPipelineConfig.getInstance().getConfig());
    }

    public <T> T getConfig(Class<T> type) {
        return (T) config.get(type);
    }

    public void initConfig(Map<Class<?>, FlinkPipelineConfiguration> inputConfig) {
        if (MapUtil.isEmpty(inputConfig)) {
            log.warn("Input configuration is empty, no changes made.");
            return;
        }
        config.putAll(inputConfig);
        log.info("Configuration initialized successfully.");
    }

    private static class SingletonHolder {
        private static final FlinkPipelineConfig INSTANCE = new FlinkPipelineConfig();
    }
}
