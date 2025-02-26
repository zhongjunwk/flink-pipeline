package com.zj.flink.common.serialization;

import cn.hutool.json.JSONUtil;
import org.apache.flink.api.common.serialization.SerializationSchema;

import java.nio.charset.StandardCharsets;

public class JsonDataSerializationSchema<T> implements SerializationSchema<T> {
    @Override
    public byte[] serialize(T recordData) {
        return JSONUtil.toJsonStr(recordData).getBytes(StandardCharsets.UTF_8);
    }
}
