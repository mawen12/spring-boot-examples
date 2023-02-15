package com.mawen.smaples.spring4.bootstrap;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.stream.Stream;

/**
 * {@link ComponentScan} 默认包引导类
 * 基于默认包扫描的方式不被 Spring 推荐，取代的是使用 {@link ComponentScan#basePackageClasses()}来指定默认包中的类即可
 *
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
