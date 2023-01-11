package com.mawen.samples.spring25.annotation;

import java.lang.annotation.*;

/**
 *
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@StringRepository
public @interface TextRepository {

    String value() default "";
}
