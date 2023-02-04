package com.mawen.samples.spring5.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * {@link Transactional @Transactional} 和 {@link Service @Service} 组合注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Transactional
@Service(value = "transactionalService")
public @interface TransactionalService {

    /**
     * @return 服务 Bean 名称
     */
    @AliasFor("value")
    String name() default "";

    /**
     * 覆盖 {@link Transactional#value()} 默认值
     *
     * @return {@link PlatformTransactionManager} Bean 名称
     */
    @AliasFor("name")
    String value() default "";

    /**
     * 建立 {@link Transactional#transactionManager()} 别名
     *
     * @return {@link PlatformTransactionManager} Bean 名称，默认关联 "txManager" Bean
     */
    @AliasFor(annotation = Transactional.class, attribute = "transactionManager")
    String transactionManager() default "txManager";

}

