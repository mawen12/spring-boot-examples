package com.mawen.spring.context.sample.configuration;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * CGLIB控制{@link Configuration}类
 * <p>
 * Spring将使用{@link Bean}注解的类缓存起来，下次调用时，直接返回类的实例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/13
 */
@Configuration
public class CGLIBDemo {

    private int counter;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_PROTOTYPE)
    public String something() {
//        printInfo(this);
        System.out.println("");
        System.out.println("Stack Trace: ");
        Stream.of(Thread.currentThread().getStackTrace()).forEach(System.out::println);
        System.out.println("");
        System.out.println("method invoked");
        return String.valueOf(++counter);
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CGLIBDemo.class);

        CGLIBDemo bean = context.getBean(CGLIBDemo.class);

        System.out.println(bean.something());
        System.out.println(bean.something());

        context.close();
    }


    private static void printInfo(Object obj) {
        System.out.println("==========================================");
        System.out.println("class name: " + obj.getClass().getName());
        System.out.println("methods: \n");
        Stream.of(obj.getClass().getMethods()).forEach(System.out::println);

        System.out.println("super class name: " + obj.getClass().getSuperclass().getName());
        System.out.println("super class annotation names: " + Arrays.toString(obj.getClass().getSuperclass().getAnnotations()));
        System.out.println("super methods:");
        Stream.of(obj.getClass().getSuperclass().getMethods()).forEach(System.out::println);

        System.out.println("interface name: " + Arrays.toString(obj.getClass().getInterfaces()));
        System.out.println("annotation names: " + Arrays.toString(obj.getClass().getAnnotations()));
    }

}
