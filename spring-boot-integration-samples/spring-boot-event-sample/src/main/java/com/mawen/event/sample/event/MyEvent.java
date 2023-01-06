package com.mawen.event.sample.event;

import org.springframework.context.ApplicationEvent;

/**
 * @author mawen
 * @since 2023/1/5
 */
public class MyEvent extends ApplicationEvent {

    private String msg;

    public MyEvent(Object source, String msg) {
        super(source);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
