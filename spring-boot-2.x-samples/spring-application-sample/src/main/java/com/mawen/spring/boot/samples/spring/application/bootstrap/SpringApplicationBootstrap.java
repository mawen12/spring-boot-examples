package com.mawen.spring.boot.samples.spring.application.bootstrap;

import com.mawen.spring.boot.samples.spring.application.config.SpringApplicationConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;

/**
 * {@link SpringApplication} 引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/18
 */
//@EnableAutoConfiguration
public class SpringApplicationBootstrap {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        ConfigurableApplicationContext context = SpringApplication.run(SpringApplicationConfiguration.class, args);
        // 输出 SpringApplicationBootstrap 对象
//        System.out.println("SpringApplicationBootstrap Bean : " + context.getBean(SpringApplicationBootstrap.class));
        // 输出 SpringApplicationConfiguration 对象
        System.out.println("SpringApplicationConfiguration Bean : " + context.getBean(SpringApplicationConfiguration.class));
        // 获取 AnnotationMBeanExporter Bean
        AnnotationMBeanExporter beanExporter = context.getBean(AnnotationMBeanExporter.class);
        // 输出 AnnotationMBeanExporter 对象
        System.out.println("AnnotationMBeanExporter Bean : " + beanExporter);
        // 关闭上下文
        context.close();
    }

}
