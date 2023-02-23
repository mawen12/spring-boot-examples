package com.mawen.spring.boot.samples.spring.application.bootstrap;

import com.mawen.spring.boot.samples.spring.application.context.HelloWorldApplicationContextInitializer;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/20
 */
public class ApplicationContextInitializerBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(Object.class)
                .initializers(new HelloWorldApplicationContextInitializer(), new HelloWorldApplicationContextInitializer())
                .run(args);
    }
}
