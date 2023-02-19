package com.mawen.spring.boot.samples.auto.configuration.bootstrap;

import com.mawen.spring.boot.samples.autoconfigure.formatter.Formatter;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
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
//                .properties("formatter.enabled=true") // 默认配置属性，"="前后不能有空格
                .run(args);// 运行
        // 待格式化对象
        Map<String, Object> data = new HashMap<>();
        data.put("name", "小马哥");
        // 获取 Formatter，来自 FormatterAutoConfiguration
        Map<String, Formatter> beans = context.getBeansOfType(Formatter.class);
        if (beans.isEmpty()) {// 如果 Bean 不存在，则抛出异常
            throw new NoSuchBeanDefinitionException(Formatter.class);
        }
        beans.forEach((beanName, formatter) -> {
            System.out.printf("[Bean name : %s] %s.format(data) : %s\n", beanName, formatter.getClass().getSimpleName(), formatter.format(data));
        });
        // 关闭当前上下文
        context.close();
    }

}
