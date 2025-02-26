package com.zj.flink.context;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.YamlPropertiesFactoryBean;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Data
@Slf4j
public class FlinkAnnotationConfigApplicationContext {

    private static final String DEFAULT_APPLICATION_PATH_NAME = "application";

    private static final String DEFAULT_APPLICATION_PATH_EXT = "yaml";

    private AnnotationConfigApplicationContext applicationContext;

    public FlinkAnnotationConfigApplicationContext(String applicationPath, Class<?> configClazz) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        FlinkAnnotationConfigApplicationContext.loadConfigFiles(applicationPath, applicationContext);
        // 注册配置类
        applicationContext.register(configClazz);
        // 刷新上下文，确保所有配置生效
        applicationContext.refresh();
        this.applicationContext = applicationContext;
    }

    private static void loadConfigFiles(String applicationConfigFileName, AnnotationConfigApplicationContext applicationContext) {
        // 手动加载 application.yaml 文件
        YamlPropertiesFactoryBean yamlFactory = new YamlPropertiesFactoryBean();
        Resource defaultConfigResource = FlinkAnnotationConfigApplicationContext.loadConfigFile(applicationConfigFileName, null);
        yamlFactory.setResources(defaultConfigResource);
        Properties properties = yamlFactory.getObject();
        if (null == properties) {
            assert defaultConfigResource != null;
            log.error(String.format("%s not found.", defaultConfigResource.getFilename()));
            throw new RuntimeException(String.format("%s not found.", defaultConfigResource.getFilename()));
        }
        ConfigurableEnvironment configurableEnvironment = applicationContext.getEnvironment();
        String active = properties.getProperty("spring.profiles.active");
        if (StringUtils.hasText(active)) {
            configurableEnvironment.setActiveProfiles(active);
            Resource resource = FlinkAnnotationConfigApplicationContext.loadConfigFile(applicationConfigFileName, active);
            yamlFactory.setResources(resource);
            properties = yamlFactory.getObject();
            if (null != properties) {
                defaultConfigResource = resource;
            } else {
                assert resource != null;
                log.error(String.format("%s not found.", resource.getFilename()));
                throw new RuntimeException(String.format("%s not found.", resource.getFilename()));
            }
        }
        String include = properties.getProperty("spring.profiles.include");
        if (StringUtils.hasText(include)) {
            List<Resource> resources = new ArrayList<>();
            resources.add(defaultConfigResource);
            List<Resource> finalResources = new ArrayList<>();
            Arrays.stream(include.split(",")).forEach(profile -> {
                configurableEnvironment.addActiveProfile(profile);
                finalResources.add(FlinkAnnotationConfigApplicationContext.loadConfigFile(applicationConfigFileName, profile));
            });
            resources.addAll(finalResources.stream().filter(Objects::nonNull).collect(Collectors.toList()));
            yamlFactory.setResources(resources.toArray(new Resource[]{}));
            properties = yamlFactory.getObject();
        }

        // 将加载的属性添加到 Spring 的环境中
        configurableEnvironment.getPropertySources().addLast(new PropertiesPropertySource("yamlProperties", properties));
    }

    private static Resource loadConfigFile(String applicationConfigFileName, String profileFileName) {
        String applicationConfigFilePath = (StringUtils.hasText(applicationConfigFileName) ? applicationConfigFileName : DEFAULT_APPLICATION_PATH_NAME) + (StringUtils.hasText(profileFileName) ? "-" + profileFileName : "") + "." + DEFAULT_APPLICATION_PATH_EXT;
        ClassPathResource classPathResource = new ClassPathResource(applicationConfigFilePath);
        if (!classPathResource.exists()) {
            log.warn(String.format("%s not found.", applicationConfigFilePath));
            return null;
        }
        return classPathResource;
    }
}
