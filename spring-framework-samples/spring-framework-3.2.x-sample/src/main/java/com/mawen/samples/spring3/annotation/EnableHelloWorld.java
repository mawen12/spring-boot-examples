package com.mawen.samples.spring3.annotation;

import com.mawen.samples.spring3.config.HelloWorldConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * HelloWorld 模块激活模式 Annotation
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(HelloWorldConfiguration.class) // 导入 HelloWorldConfiguration
public @interface EnableHelloWorld {

}
