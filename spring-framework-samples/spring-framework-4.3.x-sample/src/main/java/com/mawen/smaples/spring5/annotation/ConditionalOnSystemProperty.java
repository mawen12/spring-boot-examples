package com.mawen.smaples.spring5.annotation;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.*;

/**
 * 指定系统{@link #name() 属性名称}与{@link #value() 值}匹配条件注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnSystemPropertyCondition.class)
public @interface ConditionalOnSystemProperty {

    /**
     * @return System 属性名称
     */
    String name();

    /**
     * @return System 属性值
     */
    String value();

}
