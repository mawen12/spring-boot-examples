package com.mawen.samples.spring5.bootstrap;

import com.mawen.samples.spring5.annotation.TransactionalService;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;

import java.io.IOException;
import java.util.Set;

/**
 * 读取 @TransactionalService 注解元信息
 * - Spring 5.0 支持递归获取
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/29
 */
@TransactionalService
public class TransactionalServiceAnnotationMetadataBootstrap {

    public static void main(String[] args) throws IOException {
        String className = TransactionalServiceAnnotationMetadataBootstrap.class.getName();
        // 构建 MetadataReaderFactory 实例
        CachingMetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
        // 读取 @TransactionalService MetadataReader 信息
        MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(className);
        // 读取 @TransactionalService AnnotationMetadata 信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

        annotationMetadata.getAnnotationTypes().forEach(annotationType -> {
            Set<String> metaAnnotationTypes = annotationMetadata.getMetaAnnotationTypes(annotationType);

            metaAnnotationTypes.forEach(metaAnnotationType -> System.out.printf("注解 %s 元标注 %s\n", annotationType, metaAnnotationType));
        });
    }
}
