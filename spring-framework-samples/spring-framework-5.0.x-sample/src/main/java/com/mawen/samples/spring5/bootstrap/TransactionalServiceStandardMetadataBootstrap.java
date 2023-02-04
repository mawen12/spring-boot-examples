package com.mawen.samples.spring5.bootstrap;

import com.mawen.samples.spring5.annotation.TransactionalService;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * {@link AnnotationMetadata} 的基于Java反射API的 {@link StandardAnnotationMetadata} 示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/2
 */
@TransactionalService(name = "test")
public class TransactionalServiceStandardMetadataBootstrap {

    public static void main(String[] args) {
        // 读取 @TransactionalService AnnotationMetadata 信息
        StandardAnnotationMetadata annotationMetadata = new StandardAnnotationMetadata(TransactionalServiceStandardMetadataBootstrap.class);

        // 获取所有的元注解类型（全类名）集合
        Set<String> metaAnnotationTypes = annotationMetadata.getAnnotationTypes()
                .stream()
                .map(annotationMetadata::getMetaAnnotationTypes) // 读取单注解的元注解类型集合
                .collect(LinkedHashSet::new, Set::addAll, Set::addAll); // 合并元注解类型（全类名）集合

        metaAnnotationTypes.forEach(metaAnnotationType -> {
            // 读取元属性注解信息
            Map<String, Object> annotationAttributes = annotationMetadata.getAnnotationAttributes(metaAnnotationType);
            if (!CollectionUtils.isEmpty(annotationAttributes)) {
                annotationAttributes.forEach((name, value) -> System.out.printf("注解 %s 属性 %s = %s\n",
                        ClassUtils.getShortName(metaAnnotationType),
                        name,
                        value));
            }
        });
    }

}
