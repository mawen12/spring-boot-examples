package com.mawen.samples.spring3.config;

import com.mawen.samples.spring3.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;

/**
 * Spring XML 配置文件 ()
 */
@ImportResource("classpath:/META-INF/spring/others.xml") // 替代 <import>
@Configuration("springContextConfiguration")
@ComponentScan(basePackages = "com.mawen.samples") // 替代 <context:component-scan>
@Profile("!production") // 非生产环境
public class SpringContextConfiguration {

    @Lazy
    @Primary
    @DependsOn("springContextConfiguration")
    @Bean(name = "user")
    @Role(BeanDefinition.ROLE_APPLICATION)
    public User user() {
        User user = new User();
        user.setName("mawen");
        return user;
    }

}
