package com.mawen.samples.spring3.web;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Spring Web MVC 配置
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/5
 */
@EnableWebMvc
@Configuration
@ComponentScan(basePackages = "com.mawen.samples.spring3.web.controller")
public class SpringWebMvcConfiguration {

}
