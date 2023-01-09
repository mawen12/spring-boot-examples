package com.mawen.spring.boot.sample.bootstrap;

import com.mawen.spring.boot.sample.annotation.FirstLevelRepository;
import com.mawen.spring.boot.sample.config.FirstLevelRepositoryScanner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

import java.util.Set;

/**
 * 启动类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/9
 */
@SpringBootApplication(scanBasePackages = "com.mawen")
public class CustomAnnotationApplication implements CommandLineRunner, ApplicationContextAware {
    @Autowired
    private FirstLevelRepositoryScanner scanner;

    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(CustomAnnotationApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Set<String> annotatedClasses = scanner.findAnnotatedClasses(FirstLevelRepository.class, "com.mawen");

        for (String c : annotatedClasses) {
            System.out.println("Annotated Class : " + c);
        }

        System.out.println("MyFirstLevelRepository is bean : " + applicationContext.containsBean("myFirstLevelRepository"));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
