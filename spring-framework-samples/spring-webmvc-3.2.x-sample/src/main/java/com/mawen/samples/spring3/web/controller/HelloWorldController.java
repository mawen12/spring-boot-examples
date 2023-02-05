package com.mawen.samples.spring3.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Hello World {@link Controller}
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/5
 */
@Controller
public class HelloWorldController {

    @RequestMapping
    @ResponseBody
    public String helloWorld() {
        return "Hello, World!";
    }

}
