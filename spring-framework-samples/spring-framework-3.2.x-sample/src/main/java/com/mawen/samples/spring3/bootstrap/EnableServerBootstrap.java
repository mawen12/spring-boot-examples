package com.mawen.samples.spring3.bootstrap;

import com.mawen.samples.spring3.annotation.EnableServer;
import com.mawen.samples.spring3.server.Server;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;

/**
 * {@link EnableServer} 引导类
 */
@Configuration
@EnableServer(type = Server.Type.HTTP) // 设置 HTTP 服务器
public class EnableServerBootstrap {

    public static void main(String[] args) {
        // 构建 Annotation 配置驱动 Spring 上下文
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        // 注册 当前引导类 (被 @Configuration 标注) 到 Spring 上下文
        context.register(EnableServerBootstrap.class);
        // 启动上下文
        context.refresh();
        // 获取 Server Bean 对象，实际为 HttpServer
        Server server = context.getBean(Server.class);
        // 启动服务器
        server.start();
        // 关闭服务器
        server.stop();
        // 关闭上下文
        context.close();

    }


}
