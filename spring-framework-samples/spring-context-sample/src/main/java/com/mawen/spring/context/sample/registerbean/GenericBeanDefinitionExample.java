package com.mawen.spring.context.sample.registerbean;

import com.mawen.spring.context.sample.registerbean.model.MyBean;
import org.springframework.beans.MutablePropertyValues;
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
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();

        beanDefinition.setBeanClass(MyBean.class);
        MutablePropertyValues values = new MutablePropertyValues();
        values.add("name", "mawen");
        beanDefinition.setPropertyValues(values);

        beanFactory.registerBeanDefinition("myBean", beanDefinition);

        System.out.println("MyBean BeanDefinition exists :" + beanFactory.containsBeanDefinition("myBean"));
        System.out.println("MyBean exists :" + beanFactory.containsBean("myBean"));

        MyBean bean = beanFactory.getBean(MyBean.class);
        System.out.println(bean.getName());
    }
}
