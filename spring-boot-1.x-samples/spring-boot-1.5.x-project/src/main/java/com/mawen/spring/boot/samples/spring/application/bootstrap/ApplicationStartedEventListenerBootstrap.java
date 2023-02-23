package com.mawen.spring.boot.samples.spring.application.bootstrap;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * Spring 1.5 {@link ApplicationStartedEvent} 和 {@link ApplicationStartingEvent} 事件监听引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see ApplicationStartedEvent
 * @see ApplicationStartingEvent
 * @since 2023/2/21
 */
public class ApplicationStartedEventListenerBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Object.class)
                .listeners((ApplicationListener<ApplicationStartedEvent>) event -> {
                    System.out.println("监听 Spring Boot 事件 ApplicationStartedEvent");
                }, (ApplicationListener<ApplicationStartingEvent>) event -> {
                    System.out.println("监听 Spring Boot 事件 ApplicationStartingEvent");
                })
                .web(false) // 非 Web 应用
                .run(args) // 运行 SpringApplication
                .close(); // 关闭 Spring 应用上下文
    }
}
