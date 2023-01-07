package com.mawen.smaples.spring4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

import java.lang.annotation.Repeatable;

/**
 * 不支持 {@link Repeatable @Repeatable} 的 {@link PropertySources @PropertySources} 配置类
 */

@PropertySources({
        @PropertySource("classpath:/config/default.properties"),
        @PropertySource("classpath:/config/override.properties")
})
@Configuration
public class PropertySourcesConfiguration {
}
