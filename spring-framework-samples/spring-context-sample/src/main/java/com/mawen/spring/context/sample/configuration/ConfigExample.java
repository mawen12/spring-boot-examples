package com.mawen.spring.context.sample.configuration;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * {@link Configuration}示例
 * <p>
 * {@link Configuration}标注的类在启动时，会使用CGLIB进行子类化，生成的子类名称：ConfigExample$EnhancerBySpringCGLIB$$xxxx
 * <p>
 *
 *
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/13
 */
@Configuration
public class ConfigExample {

    private int counter;

    @Bean
    public Greeter greeter() {
        return new Greeter();
    }

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                ConfigExample.class);

        ConfigExample configExample = context.getBean(ConfigExample.class);
        printInfo(configExample);

        Greeter greeter = context.getBean(Greeter.class);
        greeter.sayHi("Joe");
        printInfo(greeter);

        context.close();
    }

    public static class Greeter {
        public void sayHi(String name) {
            System.out.println("Hi there, " + name);
        }
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
