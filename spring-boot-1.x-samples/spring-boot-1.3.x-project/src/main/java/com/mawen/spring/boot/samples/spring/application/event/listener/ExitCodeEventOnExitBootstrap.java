package com.mawen.spring.boot.samples.spring.application.event.listener;

import org.springframework.boot.ExitCodeEvent;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

/**
 * {@link ExitCodeEvent} 退出码事件 {@link org.springframework.boot.SpringApplication#exit(ApplicationContext, ExitCodeGenerator...)} 在正常结束
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see ExitCodeEvent
 * @since 2023/2/23
 */
public class ExitCodeEventOnExitBootstrap {

    @Bean
    public ExitCodeGenerator exitCodeGenerator() {
        return () -> {
            System.out.println("执行退出码(9)生成...");
            return 9;
        };
    }

    public static void main(String[] args) {
        System.exit(SpringApplication.exit(
                new SpringApplicationBuilder(ExitCodeEventOnExitBootstrap.class)
                        .listeners((ApplicationListener<ExitCodeEvent>) event -> {
                            System.out.println("监听到退出码: " + event.getExitCode());
                        })
                        .web(false) // 非 Web 应用
                        .run(args) // 运行 Spring Boot 应用
        ));
    }

}
