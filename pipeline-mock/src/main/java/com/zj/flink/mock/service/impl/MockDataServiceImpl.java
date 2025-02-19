package com.zj.flink.mock.service.impl;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.mock.config.MockConfig;
import com.zj.flink.mock.service.MockDataService;
import com.zj.flink.mock.utils.MockDataUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.streaming.api.functions.source.datagen.DataGeneratorSource;
import org.apache.flink.streaming.api.functions.source.datagen.RandomGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
@Slf4j
public class MockDataServiceImpl implements MockDataService {

    @Resource
    private MockConfig mockConfig;

    @Override
    public DataGeneratorSource<RecordData> getRecordDataDataGeneratorSource() {
        List<RecordData> dataDemoList = MockDataUtil.initDataDemo(mockConfig.getMockData(), mockConfig.getMockData().getSensitiveData(), mockConfig.getMockData().getUserData(), mockConfig.getMockData().getSrcIpData(), mockConfig.getMockData().getDestIpData());
        return new DataGeneratorSource<>(new RandomGenerator<RecordData>() {
            @Override
            public RecordData next() {
                try {
                    Thread.sleep(new Random().nextInt(1000));
                } catch (InterruptedException e) {
                    log.error(e.getMessage(), e);
                }
                RecordData randomRecordData = dataDemoList.get(new Random().nextInt(dataDemoList.size()));
                randomRecordData.setId(UUID.randomUUID().toString());
                randomRecordData.setReqTime(LocalDateTime.now());
                return randomRecordData;
            }
        }, mockConfig.getMockData().getRowsPerSecond(), null);
    }
}
