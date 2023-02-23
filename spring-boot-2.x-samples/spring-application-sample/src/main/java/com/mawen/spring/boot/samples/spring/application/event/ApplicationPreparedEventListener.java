package com.mawen.spring.boot.samples.spring.application.event;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * {@link ApplicationPreparedEvent} 事件监听器
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/21
 */
public class ApplicationPreparedEventListener implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        // 获取 Spring 应用上下文
        ConfigurableApplicationContext context = event.getApplicationContext();
        // 调整 Spring 应用上下文的ID
        context.setId("context-mawen");
        System.out.println("当前 Spring 应用上下文ID调整为: " + context.getId());
    }
}
