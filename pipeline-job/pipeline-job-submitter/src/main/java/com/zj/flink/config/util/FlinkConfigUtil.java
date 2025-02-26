package com.zj.flink.config.util;

import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlinkConfigUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Map<String, Object> convertSpringBeanConfigurationMapToBeanConfigurationMap(Map<String, Object> springbeanConfigurationMap) {
        Map<String, Object> configurationMap = new ConcurrentHashMap<>();
        if (null == springbeanConfigurationMap) {
            return configurationMap;
        }
        springbeanConfigurationMap.forEach((beanName, springBean) -> {
            try {
                //判断springBean是否有父类，如果有创建一个父类对象
                if (springBean.getClass().getSuperclass() != null) {
                    Object bean = springBean.getClass().getSuperclass().newInstance();
                    BeanUtils.copyProperties(springBean, bean);
                    configurationMap.put(beanName, bean);
                }
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });
        return configurationMap;
    }
}
