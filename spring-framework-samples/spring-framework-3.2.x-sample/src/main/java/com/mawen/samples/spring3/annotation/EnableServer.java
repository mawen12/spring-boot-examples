package com.mawen.samples.spring3.annotation;

import com.mawen.samples.spring3.server.Server;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 激活服务器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
//@Import(ServerImportSelector.class) // 导入 ServerImportSelector
@Import(ServerImportBeanDefinitionRegistrar.class)
public @interface EnableServer {

    /**
     * 设置服务器类型
     * @return non-null
     */
    Server.Type type();
}
