package com.mawen.spring.context.sample;

import com.mawen.spring.context.sample.model.MyBean;
import com.mawen.spring.context.sample.model.OtherBean;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 编程注入其他依赖
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/10
 */
public class InjectingOtherBeanExample {

    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 定义并注册 OtherBean
        GenericBeanDefinition beanOtherDef = new GenericBeanDefinition();
        beanOtherDef.setBeanClass(OtherBean.class);
        beanFactory.registerBeanDefinition("other", beanOtherDef);
        // 定义并注册 MyBean
        GenericBeanDefinition beanDef = new GenericBeanDefinition();
        beanDef.setBeanClass(MyBean.class);
        MutablePropertyValues mpv = new MutablePropertyValues();
        mpv.addPropertyValue("name", "mawen");
        mpv.addPropertyValue("otherBean", beanFactory.getBean(OtherBean.class));
        beanDef.setPropertyValues(mpv);
        beanFactory.registerBeanDefinition("myBean", beanDef);
        // 获取 MyBean 实例
        MyBean bean = beanFactory.getBean(MyBean.class);
        System.out.println("MyBean BeanDefinition exists :" + beanFactory.containsBeanDefinition("myBean"));
        System.out.println("MyBean exists :" + beanFactory.containsBean("myBean"));
        System.out.println(bean.getName());
        System.out.println(bean.getOtherBean().getClass());
    }

}
