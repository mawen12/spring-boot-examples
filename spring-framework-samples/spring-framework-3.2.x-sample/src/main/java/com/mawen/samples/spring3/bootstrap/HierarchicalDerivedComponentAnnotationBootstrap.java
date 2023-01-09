package com.mawen.samples.spring3.bootstrap;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 多层次{@link Component}派生注解 引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/7
 */
public class HierarchicalDerivedComponentAnnotationBootstrap {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/spring/context.xml");
        // 检验 myFirstLevelRepository 以及 mySecondLevelRepository 是否存在
        System.out.println("myFirstLevelRepository Bean 是否存在：" + context.containsBean("myFirstLevelRepository"));
        System.out.println("mySecondLevelRepository Bean 是否存在：" + context.containsBean("mySecondLevelRepository"));
        // 关闭上下文
        context.close();
    }

}
