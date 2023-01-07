package com.mawen.smaples.spring4;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.stream.Stream;

/**
 * {@link ComponentScan @ComponentScan} 默认包引导类
 */
@ComponentScan(basePackageClasses = DefaultPackageBootstrap.class)
public class DefaultPackageBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DefaultPackageBootstrap.class);

        System.out.println("当前 Spring 应用上下文中所有注册的 Bean 名称");
        Stream.of(context.getBeanDefinitionNames())
                .map(name -> "\t" + name) // 增加格式缩进
                .forEach(System.out::println);

        context.close();
    }
}
