package com.mawen.spring.context.sample;

import com.mawen.spring.context.sample.config.MyOneConfigBean;
import com.mawen.spring.context.sample.model.MyBean;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 基于{@link BeanFactoryPostProcessor}注册Bean
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
public class BeanFactoryPostProcessExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                MyOneConfigBean.class);

        System.out.println("MyBean BeanDefinition exists :" + context.containsBeanDefinition("myBean"));
        System.out.println("MyBean exists :" + context.containsBean("myBean"));

        MyBean bean = context.getBean(MyBean.class);
        System.out.println(bean.getName());

        context.close();

    }

}
