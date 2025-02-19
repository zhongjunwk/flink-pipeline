package com.zj.flink.config.bean;

import com.zj.flink.config.bean.annotation.FlinkConfiguration;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.flink.connector.base.DeliveryGuarantee;

import java.io.Serializable;
import java.util.Properties;

@Data
@FlinkConfiguration("kafkaConfig")
public class KafkaConfig implements Serializable, Cloneable {

    public static final String BOOTSTRAP_SERVERS = "bootstrap.servers";
    public static final String TOPIC = "topic";
    public static final String GROUP_ID = "group.id";
    private static final long serialVersionUID = 1L;
    private String deliveryGuarantee;
    private Properties producer;
    private Properties consumer;

    public String getProducerServers() {
        if (null == producer) {
            return null;
        }
        return this.getProducer().getProperty(KafkaConfig.BOOTSTRAP_SERVERS);
    }

    public String getConsumerServers() {
        if (null == consumer) {
            return null;
        }
        return this.getConsumer().getProperty(KafkaConfig.BOOTSTRAP_SERVERS);
    }

    public String getProducerTopic() {
        if (null == producer) {
            return null;
        }
        return this.getProducer().getProperty(KafkaConfig.TOPIC);
    }

    public String getConsumerTopic() {
        if (null == consumer) {
            return null;
        }
        return this.getConsumer().getProperty(KafkaConfig.TOPIC);
    }

    public String getConsumerGroupId() {
        if (null == consumer) {
            return null;
        }
        return this.getConsumer().getProperty(KafkaConfig.GROUP_ID);
    }

    public DeliveryGuarantee getDeliveryGuarantee() {
        if (StringUtils.isEmpty(deliveryGuarantee)) {
            return DeliveryGuarantee.EXACTLY_ONCE;
        }
        return DeliveryGuarantee.valueOf(deliveryGuarantee);
    }

    @Override
    public KafkaConfig clone() {
        try {
            return (KafkaConfig) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
