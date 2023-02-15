package com.mawen.spring.boot.samples.auto.configuration.bootstrap;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.admin.SpringApplicationAdminJmxAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/14
 */
@EnableAutoConfiguration(exclude = SpringApplicationAdminJmxAutoConfiguration.class)
public class EnableAutoConfigurationBootstrap {

    public static void main(String[] args) {
        new SpringApplicationBuilder(EnableAutoConfigurationBootstrap.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args) // 运行
                .close(); // 关闭当前上下文
    }

}
