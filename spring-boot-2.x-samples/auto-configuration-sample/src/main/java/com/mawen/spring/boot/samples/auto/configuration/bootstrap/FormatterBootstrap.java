package com.mawen.spring.boot.samples.auto.configuration.bootstrap;

import com.mawen.spring.boot.samples.autoconfigure.formatter.Formatter;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/15
 */
@EnableAutoConfiguration
public class FormatterBootstrap {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(FormatterBootstrap.class)
                .web(WebApplicationType.NONE) // 非 Web 应用
                .run(args);// 运行
        // 待格式化对象
        Map<String, Object> data = new HashMap<>();
        data.put("name", "小马哥");
        // 获取 Formatter，来自 FormatterAutoConfiguration
        Formatter formatter = context.getBean(Formatter.class);
        System.out.printf("formatter.format(data) : %s\n", formatter.format(data));
        // 关闭当前上下文
        context.close();
    }

}
