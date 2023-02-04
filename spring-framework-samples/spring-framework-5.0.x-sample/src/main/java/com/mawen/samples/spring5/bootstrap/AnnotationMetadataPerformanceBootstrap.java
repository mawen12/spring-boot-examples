package com.mawen.samples.spring5.bootstrap;

import com.mawen.samples.spring5.annotation.TransactionalService;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;

/**
 * 基于 ASM 的{@link AnnotationMetadataReadingVisitor} 和 基于 Java 反射的{@link StandardAnnotationMetadata}性能比对
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/2
 */
public class AnnotationMetadataPerformanceBootstrap {

    public static void main(String[] args) throws IOException {
        // 反射实现
        AnnotationMetadata standardAnnotationMetadata = new StandardAnnotationMetadata(TransactionalService.class);

        SimpleMetadataReaderFactory factory = new SimpleMetadataReaderFactory();
        MetadataReader metadataReader = factory.getMetadataReader(TransactionalService.class.getName());
        // ASM 实现
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();

        int times = 10 * 10000;
        testAnnotationMetadataPerformance(standardAnnotationMetadata, times);
        testAnnotationMetadataPerformance(annotationMetadata, times);

        times = 100 * 10000;
        testAnnotationMetadataPerformance(standardAnnotationMetadata, times);
        testAnnotationMetadataPerformance(annotationMetadata, times);

        times = 1000 * 10000;
        testAnnotationMetadataPerformance(standardAnnotationMetadata, times);
        testAnnotationMetadataPerformance(annotationMetadata, times);

        times = 10000 * 10000;
        testAnnotationMetadataPerformance(standardAnnotationMetadata, times);
        testAnnotationMetadataPerformance(annotationMetadata, times);
    }

    private static void testAnnotationMetadataPerformance(AnnotationMetadata annotationMetadata, int times) {
        long startTime = System.currentTimeMillis();
        for (int i = 0; i < times; i++) {
            annotationMetadata.getAnnotationTypes();
        }
        long costTime = System.currentTimeMillis() - startTime;
        System.out.printf("%d 次 %s.getAnnotationTypes() 方法执行消耗 %s ms\n",
                times,
                annotationMetadata.getClass().getSimpleName(),
                costTime);
    }
}
