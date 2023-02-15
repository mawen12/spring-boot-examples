package com.mawen.samples.spring2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 可配置的 Spring {@link ApplicationContext} 引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/8
 */
public class ConfigurableApplicationContextBootstrap {

    static {
        // 调整系统属性 "env"，实现 "name" bean 的定义切换
        // envValue 可能来自于 "-D" 命令行启动参数
        // 参数当不存在时，使用 "prod" 作为默认值
        String envValue = System.getProperty("env", "prod");
        System.setProperty("env", envValue);
    }

    public static void main(String[] args) {
        // 定义 XML ApplicationContext
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("META-INF/configurable-context.xml");
        // "name" bean 对象
        String value = (String) context.getBean("name");
        // "name" bean 内容输出
        System.out.printf("Bean 'name' 的内容为：%s\n", value);
        // 关闭上下文
        context.close();
    }
}
