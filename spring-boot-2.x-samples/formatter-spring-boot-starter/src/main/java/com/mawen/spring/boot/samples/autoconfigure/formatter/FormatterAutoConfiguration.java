package com.mawen.spring.boot.samples.autoconfigure.formatter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * Formatter 自动装配
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/15
 */
@Configuration
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE) // 指定装配顺序，确保在 JacksonObjectMapperConfiguration 之后，则注册 objectMapperFormatter， 如果注释掉，那么会注册 jsonFormatter
@ConditionalOnProperty(prefix = "formatter", name = "enabled", havingValue = "true",
        matchIfMissing = true) // 当属性配置不存在时，同样视作匹配
@ConditionalOnResource(resources = "META-INF/spring.factories") // 该注解存在不严谨性，可能会存在
@ConditionalOnNotWebApplication
@ConditionalOnExpression("${formatter.enabled:true}")
public class FormatterAutoConfiguration {

    /**
     * 构建 {@link DefaultFormatter} Bean
     *
     * @return {@link DefaultFormatter}
     */
    @Bean
    @ConditionalOnMissingClass(value = "com.fasterxml.jackson.databind.ObjectMapper")
    public Formatter defaultFormatter() {
        return new DefaultFormatter();
    }

    /**
     * JSON 格式 {@link Formatter} Bean
     *
     * @return {@link JsonFormatter}
     */
    @Bean
    @ConditionalOnClass(name = "com.fasterxml.jackson.databind.ObjectMapper")
    @ConditionalOnMissingBean(type = "com.fasterxml.jackson.databind.ObjectMapper")
    public Formatter jsonFormatter() {
        return new JsonFormatter();
    }

    @Bean
    @ConditionalOnBean(ObjectMapper.class)
    public Formatter objectMapperFormatter(ObjectMapper objectMapper) {
        return new JsonFormatter(objectMapper);
    }

}
