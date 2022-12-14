package com.mawen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class App {

    @RequestMapping("/")
    public String index() {
        return "Hello, My rubby!";
    }

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
}