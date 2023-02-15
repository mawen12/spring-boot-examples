package com.mawen.samples.spring3.web;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * Spring Web MVC {@link WebApplicationInitializer} 实现
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/5
 */
public class SpringWebMvcServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    /**
     * 提供应用上下文所需的配置类，用于生成 Root WebApplicationContext
     *
     * @see AbstractAnnotationConfigDispatcherServletInitializer#createRootApplicationContext()
     * @return 配置类集合
     */
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[0];
    }

    /**
     * 提供上下文需要的配置类，用于生成 DispatcherServlet WebApplicationContext
     *
     * @see AbstractAnnotationConfigDispatcherServletInitializer#createServletApplicationContext()
     * @return 配置类集合
     */
    @Override
    protected Class<?>[] getServletConfigClasses() { // DispatcherServlet 配置Bean
        return of(SpringWebMvcConfiguration.class);
    }

    @Override
    protected String[] getServletMappings() { // DispatcherServlet URL Pattern 映射
        return of("/*");
    }

    private static <T> T[] of(T... values) { // 便利 API，减少 new T[] 代码
        return values;
    }
}
