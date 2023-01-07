package com.mawen.samples.spring25.bootstrap;

import com.mawen.samples.spring25.repository.NameRepository;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * {@link Component} "派生"注解 引导程序
 */
public class DerivedComponentAnnotationBootstrap {

    static {
        // 解决 Spring 2.5.x 不兼容 Java 8 的问题
        // 同时，请注意 Java Security 策略，必须具备 PropertyPermission
        System.setProperty("java.version", "1.7.0");
    }

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext();
        context.setConfigLocation("classpath:/META-INF/spring/context.xml");
        context.refresh();
        NameRepository nameRepository = (NameRepository) context.getBean("chineseNameRepository");
        System.out.printf("nameRepository.findAll() = %s \n", nameRepository.findAll());

        context.close();
    }
}
