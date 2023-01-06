package com.mawen.event.sample.controller;

import com.mawen.event.sample.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mawen
 * @since 2023/1/5
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/update")
    public void update() {
        userService.updateUser();
    }

    @RequestMapping("/update1")
    public void update1() {
        userService.updateUser1();
    }

}
