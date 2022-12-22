package com.mawen.samples.spring25.annotation;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component // 测试多层次 @Component 派生，请将当前注释
//@Repository // 测试多层次 @Component 派生，请将当前反注释，并且将 spring-context 升级到 3.0.0.RELEASE
public @interface StringRepository {

    /**
     * 属性方法必须与 {@link Component#value()} 保持一致
     * @return Bean 的名称
     */
    String value() default "";
}
