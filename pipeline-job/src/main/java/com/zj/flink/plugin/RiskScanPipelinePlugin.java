package com.zj.flink.plugin;

import com.zj.flink.common.beans.RecordData;
import com.zj.flink.config.bean.FlinkConfig;
import com.zj.flink.config.bean.RiskStrategyConfig;
import com.zj.flink.config.bean.WhiteListConfig;
import com.zj.flink.pipeline.core.annotation.PluginComponent;
import com.zj.flink.pipeline.core.plugins.AbstractProcessPipelinePlugin;
import com.zj.flink.risk.beans.RiskStrategy;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.ProcessAllWindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@PluginComponent("riskScanPipelinePlugin")
public class RiskScanPipelinePlugin extends AbstractProcessPipelinePlugin<RecordData> {

    private WhiteListConfig whiteListConfig;
    private RiskStrategyConfig riskStrategyConfig;

    @Override
    public void init(FlinkConfig flinkConfig) {
        this.whiteListConfig = flinkConfig.getConfig(WhiteListConfig.class);
        this.riskStrategyConfig = flinkConfig.getConfig(RiskStrategyConfig.class);
    }

    @Override
    public DataStream<RecordData> process(DataStream<RecordData> dataStream, StreamExecutionEnvironment env) {
        List<RiskStrategy> riskStrategyList = riskStrategyConfig.getRiskStrategyList();
        List<String> distIpList = whiteListConfig.getDistIpList();
        // 定义滑动窗口，这里设置窗口大小为1分钟，滑动步长为30秒
        SingleOutputStreamOperator<RecordData> dataStreamResult = dataStream.keyBy(value -> value.getHostname() + "-" + value.getSrcIp()).windowAll(SlidingProcessingTimeWindows.of(Time.minutes(1), Time.seconds(30))).process(new ProcessAllWindowFunction<RecordData, RecordData, TimeWindow>() {
            @Override
            public void process(ProcessAllWindowFunction<RecordData, RecordData, TimeWindow>.Context context, Iterable<RecordData> iterable, Collector<RecordData> collector) throws Exception {
                // 对窗口内的数据进行处理
                List<RecordData> recordDataList = StreamSupport.stream(iterable.spliterator(), true).filter(v -> distIpList.contains(v.getHostname())).collect(Collectors.toList());
                riskStrategyList.forEach(riskStrategy -> {
                    AtomicBoolean hit = new AtomicBoolean(true);
                    riskStrategy.getRiskStrategyFactorsList().forEach(riskStrategyFactors -> {
                        if (null != riskStrategyFactors.getLimitNum() && recordDataList.size() < riskStrategyFactors.getLimitNum()) {
                            hit.set(false);
                        }
                    });
                    if (hit.get()) {
                        recordDataList.forEach(riskData -> {
                            riskData.getRiskStrategySet().add(riskStrategy);
                        });
                    }
                });
                recordDataList.forEach(collector::collect);
            }
        });
        return dataStreamResult;
    }
}
