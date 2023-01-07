package com.mawen.smaples.spring4.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.lang.annotation.Repeatable;

/**
 * 支持 {@link Repeatable @Repeatable} 的 {@link PropertySource @PropertySource} 配置类
 * <p>
 * 依赖于 Java 8
 */
@PropertySource("classpath:/config/default.properties")
@PropertySource("classpath:/config/override.properties") // 重复标注 @PropertySource
@Configuration
public class RepeatablePropertySourceConfiguration {
}
