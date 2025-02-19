package com.zj.flink.mock.service;

import com.zj.flink.common.beans.RecordData;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;

public interface MockDataService {
    DataGeneratorSource<RecordData> getRecordDataDataGeneratorSource();
}
