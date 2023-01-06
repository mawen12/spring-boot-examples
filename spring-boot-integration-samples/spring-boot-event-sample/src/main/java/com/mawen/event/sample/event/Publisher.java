package com.mawen.event.sample.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/**
 * @author mawen
 * @since 2023/1/5
 */
@Component
public class Publisher implements ApplicationEventPublisherAware {

    private ApplicationEventPublisher publisher;

    public void publish(String msg) {
        publisher.publishEvent(new MyEvent(this, msg));
    }

    public void publish(ApplicationEvent event) {
        publisher.publishEvent(event);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.publisher = applicationEventPublisher;
    }
}
