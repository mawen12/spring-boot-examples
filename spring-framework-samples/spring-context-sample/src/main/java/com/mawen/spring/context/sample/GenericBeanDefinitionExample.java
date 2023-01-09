package com.mawen.spring.context.sample;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 基于{@link GenericBeanDefinition}注册Bean
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/9
 */
public class GenericBeanDefinitionExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory context = new DefaultListableBeanFactory();

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(MyBean.class);

        context.registerBeanDefinition("myBean", beanDefinition);

        System.out.println("MyBean BeanDefinition exists :" + context.containsBeanDefinition("myBean"));
        System.out.println("MyBean exists :" + context.containsBean("myBean"));
    }
}
