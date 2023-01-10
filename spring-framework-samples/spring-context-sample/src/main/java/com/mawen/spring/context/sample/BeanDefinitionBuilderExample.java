package com.mawen.spring.context.sample;

import com.mawen.spring.context.sample.model.MyBean;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * 基于{@link BeanDefinitionBuilder}注册Bean
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/10
 */
public class BeanDefinitionBuilderExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        AbstractBeanDefinition beanDefinition = BeanDefinitionBuilder.rootBeanDefinition(MyBean.class)
                .addPropertyValue("name", "mawen")
                .getBeanDefinition();

        beanFactory.registerBeanDefinition("myBean", beanDefinition);

        System.out.println("MyBean BeanDefinition exists :" + beanFactory.containsBeanDefinition("myBean"));
        System.out.println("MyBean exists :" + beanFactory.containsBean("myBean"));

        MyBean bean = beanFactory.getBean(MyBean.class);
        System.out.println(bean.getName());
    }

}
