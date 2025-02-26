package com.zj.flink.config.util;

import com.zj.flink.config.FlinkPipelineConfiguration;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FlinkConfigUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    public static Map<Class<?>, FlinkPipelineConfiguration> convertSpringBeanConfigurationMapToBeanConfigurationMap(Map<String, FlinkPipelineConfiguration> springbeanConfigurationMap) {
        Map<Class<?>, FlinkPipelineConfiguration> configurationMap = new ConcurrentHashMap<>();
        if (null == springbeanConfigurationMap) {
            return configurationMap;
        }
        springbeanConfigurationMap.forEach((beanName, springBean) -> {
            if (null != springBean.getRealSuperClass()) {
                try {
                    Object bean = springBean.getRealSuperClass().newInstance();
                    BeanUtils.copyProperties(springBean, bean);
                    configurationMap.put(bean.getClass(), (FlinkPipelineConfiguration) bean);
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        return configurationMap;
    }
}
