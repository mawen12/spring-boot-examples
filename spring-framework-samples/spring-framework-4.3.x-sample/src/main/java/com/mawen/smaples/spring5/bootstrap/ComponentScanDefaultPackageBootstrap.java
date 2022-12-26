package com.mawen.smaples.spring5.bootstrap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.stream.Stream;

/**
 * {@link ComponentScan @ComponentScan} 默认包引导类
 */
@ComponentScan(basePackages = "") // 扫描默认根包
public class ComponentScanDefaultPackageBootstrap {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ComponentScanDefaultPackageBootstrap.class);
        System.out.println("当前 Spring 应用上下文中所有注册的 Bean 名称:");
        Stream.of(context.getBeanDefinitionNames())
                .map(name -> "\t" + name)
                .forEach(System.out::println);
        // 关闭上下文
        context.close();
    }

}
