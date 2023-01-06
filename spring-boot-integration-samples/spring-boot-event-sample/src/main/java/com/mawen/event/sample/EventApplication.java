package com.mawen.event.sample;

import com.mawen.event.sample.event.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 主启动类
 *
 * @author mawen
 * @since 2023/1/5
 */
@EnableAsync
@RestController
@SpringBootApplication
public class EventApplication {

    @Autowired
    private Publisher publisher;

    public static void main(String[] args) {
        SpringApplication.run(EventApplication.class, args);
    }

    @RequestMapping("/send")
    public void send() {
        publisher.publish("Hello World");
    }
}
