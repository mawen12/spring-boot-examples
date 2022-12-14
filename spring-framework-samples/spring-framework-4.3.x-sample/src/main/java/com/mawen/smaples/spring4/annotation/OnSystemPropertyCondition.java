package com.mawen.smaples.spring4.annotation;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;

import java.util.Objects;

/**
 * 指定系统属性名称与值匹配条件
 */
public class OnSystemPropertyCondition implements Condition {

    @Override
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        // 获取 ConditionalOnSystemProperty 所有的属性方法值
        MultiValueMap<String, Object> attributes = annotatedTypeMetadata.getAllAnnotationAttributes(ConditionalOnSystemProperty.class.getName());
        // 获取 ConditionalOnSystemProperty#name() 方法值（单值）
        String propertyName = (String) attributes.getFirst("name");
        // 获取 ConditionalOnSystemProperty#value() 方法值（单值）
        String propertyValue = (String) attributes.getFirst("value");
        // 获取 系统属性值
        String systemPropertyValue = System.getProperty(propertyName);
        // 比较 系统属性值 与 ConditionalOnSystemProperty#value() 方法值 是否相等
        if (Objects.equals(propertyValue, systemPropertyValue)) {
            System.out.printf("系统属性[名称 : %s] 找到匹配值 : %s\n", propertyName, propertyValue);
            return true;
        }
        return false;
    }
}
