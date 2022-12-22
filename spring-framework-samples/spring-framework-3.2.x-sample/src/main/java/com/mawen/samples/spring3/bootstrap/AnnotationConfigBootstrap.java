package com.mawen.samples.spring3.bootstrap;

import com.mawen.samples.spring3.config.SpringContextConfiguration;
import com.mawen.samples.spring3.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Annotation 配置驱动引导程序
 */
public class AnnotationConfigBootstrap {

    public static void main(String[] args) {
        // 构建 Annotation 配置驱动 Spring 上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册 配置Bean SpringContextConfiguration 到 Spring 上下文
        context.register(SpringContextConfiguration.class);
        // 启动上下文
        context.refresh();
        // 获取名称为 "user" Bean 对象
        User user = context.getBean("user", User.class);
        // 输出用户名
        System.out.printf("user.getName() = %s \n", user.getName());
        // 关闭 Spring 上下文
        context.close();
    }

}
