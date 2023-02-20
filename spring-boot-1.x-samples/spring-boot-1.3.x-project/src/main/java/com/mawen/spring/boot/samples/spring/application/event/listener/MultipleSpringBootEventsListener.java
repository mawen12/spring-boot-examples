package com.mawen.spring.boot.samples.spring.application.event.listener;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationFailedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.event.SpringApplicationEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.core.Ordered;

import java.util.Random;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/19
 */
public class MultipleSpringBootEventsListener implements SmartApplicationListener {

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        // 支持的事件类型
        return ApplicationReadyEvent.class.equals(eventType) ||
                ApplicationFailedEvent.class.equals(eventType);
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        // SpringApplicationContext 均以 SpringApplication 作为配置源
        return SpringApplication.class.equals(sourceType);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof ApplicationReadyEvent) {
            // 当事件为 ApplicationReadyEvent 时，随机跑出异常
            if (new Random().nextBoolean()) {
                throw new RuntimeException("ApplicationReadyEvent 事件监听异常！");
            }
        }
        System.out.println("MultipleSpringBootEventsListener 监听到事件 : " + event.getClass().getSimpleName());
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }

    @EventListener({ApplicationReadyEvent.class, ApplicationFailedEvent.class})
    public void onSpringBootEvent(SpringApplicationEvent event) {
        System.out.println("@EventListener 监听到事件 : " + event.getClass().getSimpleName());
    }
}
