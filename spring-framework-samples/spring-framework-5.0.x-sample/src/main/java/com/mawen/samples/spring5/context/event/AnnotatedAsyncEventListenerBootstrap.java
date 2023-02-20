package com.mawen.samples.spring5.context.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * {@link EventListener @EventListener} {@link Async 异步}监听方法引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/19
 */
public class AnnotatedAsyncEventListenerBootstrap {

    public static void main(String[] args) {
        // 创建 注解驱动 Spring 应用上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册 异步 @EventListener 类 MyAsyncEventListener
        context.register(MyAsyncEventListener.class);
        System.out.println("Spring 应用上下文正在初始化...");
        // 初始化上下文
        context.refresh();
        // 关闭上下文
        context.close();
    }

    @EnableAsync
    public static class MyAsyncEventListener {

        @EventListener(ContextRefreshedEvent.class)
        @Async
        public Boolean onContextRefreshedEvent(ContextRefreshedEvent event) {
            System.out.printf("[ 线程 %s ]", Thread.currentThread().getName());
            System.out.println(" MyAsyncEventListener : " + event.getClass().getSimpleName());
            return true;
        }
    }


}
