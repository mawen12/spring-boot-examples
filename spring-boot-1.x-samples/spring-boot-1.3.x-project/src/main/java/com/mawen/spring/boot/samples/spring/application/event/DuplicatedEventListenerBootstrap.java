package com.mawen.spring.boot.samples.spring.application.event;

import com.mawen.spring.boot.samples.spring.application.event.listener.ContextRefreshedEventListener;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Spring Boot {@link ContextRefreshedEventListener} 重复监听引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/19
 */
public class DuplicatedEventListenerBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DuplicatedEventListenerBootstrap.class)
                .web(false) // 非 Web 应用
                .run(args) // 运行 SpringApplication
                .close(); // 关闭 ConfigurableApplicationContext
    }
}
