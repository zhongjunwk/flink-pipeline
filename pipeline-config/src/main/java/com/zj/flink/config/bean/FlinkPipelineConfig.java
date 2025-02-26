package com.zj.flink.config.bean;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.zj.flink.config.FlinkPipelineConfiguration;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
public class FlinkPipelineConfig implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(FlinkPipelineConfig.class);
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
        return ObjectUtil.deserialize(Base64Decoder.decodeStr(configMapSerializableStr, FlinkPipelineConfig.DEFAULT_CHARSET).getBytes(FlinkPipelineConfig.DEFAULT_CHARSET));
    }

    public static String buildConfigMapSerializableStr(Map<Class<?>, FlinkPipelineConfiguration> configMap) {
        if (MapUtil.isEmpty(configMap)) {
            return "";
        }
        return Base64Encoder.encode(new String(ObjectUtil.serialize(configMap), FlinkPipelineConfig.DEFAULT_CHARSET), FlinkPipelineConfig.DEFAULT_CHARSET);
    }

    public static String buildConfigMapSerializableStr() {
        return FlinkPipelineConfig.buildConfigMapSerializableStr(FlinkPipelineConfig.getInstance().getConfig());
    }

    public <T> T getConfig(Class<T> type) {
        return (T) config.get(type);
    }

    public void initConfig(Map<Class<?>, FlinkPipelineConfiguration> inputConfig) {
        if (MapUtil.isEmpty(inputConfig)) {
            logger.warn("Input configuration is empty, no changes made.");
            return;
        }
        config.putAll(inputConfig);
        logger.info("Configuration initialized successfully.");
    }

    private static class SingletonHolder {
        private static final FlinkPipelineConfig INSTANCE = new FlinkPipelineConfig();
    }
}
