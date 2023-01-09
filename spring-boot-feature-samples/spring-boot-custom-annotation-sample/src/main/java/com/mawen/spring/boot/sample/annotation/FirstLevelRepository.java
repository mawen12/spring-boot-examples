package com.mawen.spring.boot.sample.annotation;

import java.lang.annotation.*;

/**
 * 自定义仓储注解
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/9
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface FirstLevelRepository {
}
