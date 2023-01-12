package com.mawen.spring.context.sample;

import com.mawen.spring.context.sample.config.MyTwoConfigBean;
import com.mawen.spring.context.sample.model.MyBean;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于{@link BeanDefinitionRegistryPostProcessor}注册Bean
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
public class BeanDefinitionRegistryPostProcessorExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                MyTwoConfigBean.class);

        System.out.println("MyBean BeanDefinition exists :" + context.containsBeanDefinition("myBeanName"));
        System.out.println("MyBean exists :" + context.containsBean("myBeanName"));

        MyBean bean = context.getBean(MyBean.class);
        System.out.println(bean.getName());


        context.close();


    }
}
