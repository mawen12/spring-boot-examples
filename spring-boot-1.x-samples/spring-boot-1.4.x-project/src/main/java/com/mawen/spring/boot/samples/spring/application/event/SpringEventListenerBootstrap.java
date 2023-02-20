package com.mawen.spring.boot.samples.spring.application.event;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationListener;

/**
 * Spring 事件监听器引导类
 * 来自于 Spring Boot META-INF/spring.factories 的 {@link ApplicationListener} Spring 事件监听器
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/19
 */
public class SpringEventListenerBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Object.class) // 非 @Configuration 充当配置源
                .web(false) // 非 Web 应用
                .listeners(event -> System.out.println("SpringApplication 事件监听器 : " + event.getClass().getSimpleName())) // 添加 ApplicationListener
                .run(args) // 运行 SpringApplication
                .close(); // 关闭 ConfigurableApplicationContext
    }
}
