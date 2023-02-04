package com.mawen.samples.spring5.bootstrap;

import com.mawen.samples.spring5.annotation.TransactionalService;
import com.mawen.samples.spring5.bean.TransactionalServiceBean;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.AnnotatedElement;

/**
 * {@link AnnotationAttributes} 示例
 * - {@link AnnotationAttributes} 是 Spring 注解属性抽象
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/2
 */
public class AnnotationAttributesBootstrap {

    public static void main(String[] args) {
//        AnnotatedElement annotatedElement = TransactionalService.class;
        AnnotatedElement annotatedElement = TransactionalServiceBean.class;


        // 获取 @Service 注解属性独享
        AnnotationAttributes serviceAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(annotatedElement, Service.class);

        // 获取 @Transactional 注解属性独享
        AnnotationAttributes transactionalAttributes = AnnotatedElementUtils.getMergedAnnotationAttributes(annotatedElement, Transactional.class);

        // 输出
        print(serviceAttributes);
        print(transactionalAttributes);
    }

    private static void print(AnnotationAttributes annotationAttributes) {
        System.out.printf("注解 %s 属性集合: \n", annotationAttributes.annotationType().getName());

        annotationAttributes.forEach((name, value) -> System.out.printf("\t 属性 %s : %s \n", name, value));
    }

}
