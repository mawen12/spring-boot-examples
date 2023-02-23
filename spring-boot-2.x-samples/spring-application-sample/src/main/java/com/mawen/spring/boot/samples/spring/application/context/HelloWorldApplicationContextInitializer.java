package com.mawen.spring.boot.samples.spring.application.context;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 输出 Hello World 的 {@link ApplicationContextInitializer} 实现
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/20
 */
public class HelloWorldApplicationContextInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("HelloWorldApplicationContextInitializer : Hello World!");
    }

    /**
     * 解决重复注册的问题
     *
     * @return
     */
    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    /**
     * 解决重复注册的问题
     *
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {
        return getClass().equals(obj.getClass());
    }
}
