package com.mawen.spring.context.sample.config;

import com.mawen.spring.context.sample.model.MyBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * 实现{@link BeanFactoryPostProcessor}注册Spring Bean
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/10
 */
public class MyConfigBean implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        GenericBeanDefinition beanDef = new GenericBeanDefinition();
        beanDef.setBeanClass(MyBean.class);
        beanDef.getPropertyValues().add("name", "mawen");

        ((DefaultListableBeanFactory)beanFactory).registerBeanDefinition("myBean", beanDef);
    }
}
