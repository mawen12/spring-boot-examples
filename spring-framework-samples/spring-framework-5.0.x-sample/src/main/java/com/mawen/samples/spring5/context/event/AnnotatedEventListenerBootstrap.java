package com.mawen.samples.spring5.context.event;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

/**
 * {@link EventListener @EventListener 注解驱动事件监听器} 引导类，检验；
 * <ul>
 *     <li>{@link EventListener @EventListener} private 方法有返回值是否正常工作</li>
 *     <li>在抽象类中声明的{@link EventListener @EventListener} private 方法，是否能在子类 Bean 中正常工作</li>
 * </ul>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/19
 */
public class AnnotatedEventListenerBootstrap {

    public static void main(String[] args) {
        // 创建 注解驱动 Spring 应用上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册 @EventListener 类 MyEventListener
        context.register(MyEventListener.class);
        // 初始化上下文
        context.refresh();
        // 关闭上下文
        context.close();
    }

    /**
     * {@link EventListener} 抽象类
     */
    public static abstract class AbstractEventListener {
        @EventListener(ContextRefreshedEvent.class)
        private void onContextRefreshedEvent(ContextRefreshedEvent event) {
            System.out.println("AbstractEventListener : " + event.getClass().getSimpleName());
        }
    }

    /**
     * {@link EventListener} 具体类，作为 Spring Bean，继承 {@link AbstractEventListener}
     */
    public static class MyEventListener extends AbstractEventListener {

        @EventListener(ContextClosedEvent.class)
        private boolean OnContextClosedEvent(ContextClosedEvent event) {
            System.out.println("MyEventListener : " + event.getClass().getSimpleName());
            return true;
        }
    }
}


