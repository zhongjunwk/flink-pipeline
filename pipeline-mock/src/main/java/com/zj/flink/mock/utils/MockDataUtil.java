package com.zj.flink.mock.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.zj.flink.common.beans.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class MockDataUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static List<RecordData> initDataDemo(MockData mockData, Map<String, List<String>> sensitiveDataMap, List<UserData> userDataList, List<String> srcIpData, List<String> distIpData) {
        final List<RecordData> dataDemoList = new ArrayList<>();
        BodyInfoData bodyInfoData;
        UserData userData;
        String[] dstIpArr;
        String dstIp;
        for (int i = 0; i < mockData.getDataSize(); i++) {
            dstIp = distIpData.get(new Random().nextInt(distIpData.size()));
            dstIpArr = dstIp.split(":");
            userData = userDataList.get(new Random().nextInt(userDataList.size()));
            Map<String, List<String>> sensitiveDataMapTemp = new HashMap<>(sensitiveDataMap);
            // 移除需要删除的键
            sensitiveDataMapTemp.keySet().removeIf(k -> !RandomUtil.randomBoolean());
            // 处理剩下的键值对
            sensitiveDataMapTemp.replaceAll((k, v) -> {
                // 保留需要保留的元素
                return v.stream().filter(s -> RandomUtil.randomBoolean()) // 保留需要保留的元素
                        .collect(Collectors.toList());// 收集到新列表
            });
            // 删除空的列表项
            sensitiveDataMapTemp.entrySet().removeIf(entry -> entry.getValue().isEmpty());
            bodyInfoData = BodyInfoData.builder()
                    .userInfo(UserInfoData.builder()
                            .userName(userData.getUserName())
                            .name(userData.getName())
                            .sex(userData.getSex())
                            .age(userData.getAge())
                            .build())
                    .sensitiveDataMap(sensitiveDataMapTemp)
                    .build();
            dataDemoList.add(
                    RecordData.builder()
                            .id(UUID.fastUUID().toString())
                            .srcIp(srcIpData.get(new Random().nextInt(srcIpData.size())))
                            .destIp(dstIpArr[0])
                            .srcPort(String.valueOf(new Random().nextInt(1000)))
                            .destPort(dstIpArr[1])
                            .protocol("http")
                            .body(JSONUtil.toJsonStr(bodyInfoData))
                            .hostname(dstIp)
                            .reqTime(LocalDateTime.now())
                            .build()
            );
        }
        return dataDemoList;
    }

}
