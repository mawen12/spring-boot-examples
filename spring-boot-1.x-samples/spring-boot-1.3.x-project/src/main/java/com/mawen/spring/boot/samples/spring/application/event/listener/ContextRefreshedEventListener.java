package com.mawen.spring.boot.samples.spring.application.event.listener;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * 监听 {@link ContextRefreshedEvent} 的 {@link ApplicationListener} 实现
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/19
 */
public class ContextRefreshedEventListener implements ApplicationListener<ContextRefreshedEvent> {

    private final long id;

    public ContextRefreshedEventListener() {
        // id 使用构造时的纳秒时间
        this.id = System.nanoTime();
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        System.out.printf("ContextRefreshedEventListener[id : %d] 接收到事件 : %s\n",
                id, event.getClass().getSimpleName());
    }

    // 覆盖 hashCode，使得 ApplicationListener 重复添加
    public int hashCode() {
        // 返回执行时的纳秒时间
        return (int) System.nanoTime();
    }
}
