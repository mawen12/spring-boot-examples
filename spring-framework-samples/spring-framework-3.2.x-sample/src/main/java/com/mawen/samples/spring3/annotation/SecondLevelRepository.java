package com.mawen.samples.spring3.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 继承 {@link FirstLevelRepository} 验证 {@link Component} 层次性
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/7
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FirstLevelRepository
public @interface SecondLevelRepository {

    String value() default "";
}
