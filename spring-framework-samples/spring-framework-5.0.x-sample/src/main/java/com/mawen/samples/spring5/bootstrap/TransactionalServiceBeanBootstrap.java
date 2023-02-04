package com.mawen.samples.spring5.bootstrap;

import com.mawen.samples.spring5.bean.TransactionalServiceBean;
import jdk.jfr.AnnotationElement;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionException;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.SimpleTransactionStatus;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * 隐式覆盖
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/2
 */
@Configuration
@ComponentScan(basePackageClasses = TransactionalServiceBean.class)
@EnableTransactionManagement // 激活事务管理
public class TransactionalServiceBeanBootstrap {

    public static void main(String[] args) {
        // 注册当前引导类作为 Configuration Class
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TransactionalServiceBeanBootstrap.class);

        // 获取所有 TransactionalServiceBean 类型Bean，其中 key 为 Bean 名称
        Map<String, TransactionalServiceBean> beansMap = context.getBeansOfType(TransactionalServiceBean.class);
        beansMap.forEach((beanName, bean) -> {
            System.out.printf("Bean 名称 : %s，对象 : %s\n",
                    beanName,
                    bean);

            StandardAnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(TransactionalServiceBean.class);
            Set<String> metaAnnotationTypes = annotationMetadata.getAnnotationTypes()
                    .stream()
                    .map(annotationMetadata::getMetaAnnotationTypes)
                    .collect(LinkedHashSet::new, Set::addAll, Set::addAll);

            metaAnnotationTypes.forEach(metaAnnotationType -> {
                Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(metaAnnotationType);
                if (!CollectionUtils.isEmpty(annotationAttributes)) {
                    annotationAttributes.forEach((name, value) -> System.out.printf("注解 %s 属性 %s = %s\n",
                            ClassUtils.getShortName(metaAnnotationType),
                            name,
                            value));
                }
            });

            bean.save();
        });



        context.close();
    }


    @Bean("txManager")
    public PlatformTransactionManager txManager() {
        return new PlatformTransactionManager() {
            @Override
            public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
                return new SimpleTransactionStatus();
            }

            @Override
            public void commit(TransactionStatus transactionStatus) throws TransactionException {
                System.out.println("txManager ： 事务提交...");
            }

            @Override
            public void rollback(TransactionStatus transactionStatus) throws TransactionException {

            }
        };
    }

    @Bean("txManager2")
    public PlatformTransactionManager txManger2() {
        return new PlatformTransactionManager() {
            @Override
            public TransactionStatus getTransaction(TransactionDefinition transactionDefinition) throws TransactionException {
                return new SimpleTransactionStatus();
            }

            @Override
            public void commit(TransactionStatus transactionStatus) throws TransactionException {
                System.out.println("txManager2 : 事务提交...");
            }

            @Override
            public void rollback(TransactionStatus transactionStatus) throws TransactionException {

            }
        };
    }
}
