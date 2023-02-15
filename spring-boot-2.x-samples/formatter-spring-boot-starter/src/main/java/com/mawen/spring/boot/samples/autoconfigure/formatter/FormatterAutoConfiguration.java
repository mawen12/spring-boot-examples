package com.mawen.spring.boot.samples.autoconfigure.formatter;

import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Formatter 自动装配
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/15
 */
@Configuration
@ConditionalOnProperty(prefix = "formatter", name = "enabled", havingValue = "true",
        matchIfMissing = true) // 当属性不存在时，同样视作匹配
@ConditionalOnResource(resources = "META-INF/spring.factories")
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

}
