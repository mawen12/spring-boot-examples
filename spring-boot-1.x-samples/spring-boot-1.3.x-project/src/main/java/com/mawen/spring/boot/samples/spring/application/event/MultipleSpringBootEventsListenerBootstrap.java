package com.mawen.spring.boot.samples.spring.application.event;

import com.mawen.spring.boot.samples.spring.application.event.listener.MultipleSpringBootEventsListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * {@link MultipleSpringBootEventsListener} 多 Spring Boot 事件监听器引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/19
 */
public class MultipleSpringBootEventsListenerBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(MultipleSpringBootEventsListener.class) // 注册为 Spring Bean
                .web(false) // 非 Web 应用
                .run(args) // 运行 SpringApplication
                .close(); // 关闭 ConfigurableApplicationContext
    }
}
