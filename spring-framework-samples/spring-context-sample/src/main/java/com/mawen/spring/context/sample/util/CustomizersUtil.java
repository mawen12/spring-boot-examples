package com.mawen.spring.context.sample.util;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

/**
 * {@link BeanDefinitionCustomizer}工具类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
public class CustomizersUtil {

    public static void prototypeScoped(BeanDefinition bd) {
        bd.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
    }

    public static void lazy(BeanDefinition bd) {
        bd.setLazyInit(true);
    }

    public static void defaultInitMethod(BeanDefinition bd) {
        bd.setInitMethodName("init");
    }

    public static void defaultDestroyMethod(BeanDefinition bd) {
        bd.setDestroyMethodName("destroy");
    }

}
