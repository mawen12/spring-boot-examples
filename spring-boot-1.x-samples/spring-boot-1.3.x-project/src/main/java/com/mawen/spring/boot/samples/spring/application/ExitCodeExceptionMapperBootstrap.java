package com.mawen.spring.boot.samples.spring.application;

import org.springframework.boot.ExitCodeExceptionMapper;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

/**
 * {@link ExitCodeExceptionMapper} 引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/23
 */
public class ExitCodeExceptionMapperBootstrap {

    @Bean
    public ExitCodeExceptionMapper exitCodeExceptionMapper() {
        return (throwable) -> 128;
    }

    public static void main(String[] args) {
        new SpringApplicationBuilder(ExitCodeExceptionMapperBootstrap.class)
                .listeners((ApplicationListener<ApplicationReadyEvent>) event -> {
                    throw new RuntimeException(event.getClass().getSimpleName());
                })
                .web(false)
                .run(args)
                .close();
    }
}
