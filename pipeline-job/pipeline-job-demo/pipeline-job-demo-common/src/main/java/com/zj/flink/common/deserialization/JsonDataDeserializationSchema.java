package com.zj.flink.common.deserialization;

import cn.hutool.json.JSONUtil;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.TypeExtractor;
import org.apache.flink.connector.kafka.source.reader.deserializer.KafkaRecordDeserializationSchema;
import org.apache.flink.util.Collector;
import org.apache.kafka.clients.consumer.ConsumerRecord;

import java.nio.charset.StandardCharsets;

public class JsonDataDeserializationSchema<T> implements KafkaRecordDeserializationSchema<T> {

    private Class<T> type;

    public static <T> JsonDataDeserializationSchema<T> of(Class<T> type) {
        JsonDataDeserializationSchema jsonDataDeserializationSchema = new JsonDataDeserializationSchema<>();
        jsonDataDeserializationSchema.type = type;
        return jsonDataDeserializationSchema;
    }

    @Override
    public void deserialize(ConsumerRecord<byte[], byte[]> record, Collector<T> out) {
        out.collect(JSONUtil.toBean(new String(record.value(), StandardCharsets.UTF_8), this.type));
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return TypeExtractor.createTypeInfo(this.type);
    }
}
