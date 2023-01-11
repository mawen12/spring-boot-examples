package com.mawen.samples.spring25.bootstrap;

import com.mawen.samples.spring25.annotation.StringRepository;
import com.mawen.samples.spring25.annotation.TextRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

/**
 * 解析{@link Annotation}层次性
 * <p>
 * {@link StringRepository}分别使用{@link Repository}和{@link Component}的区别
 * <p>
 * 需要注意{@link org.springframework.core.type.classreading.AnnotationMetadataReadingVisitor}它们在2.5.6.SEC03和3.0.0.RELEASE的差异
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
class DerivedAnnotationExample {

    public static void main(String[] args) {
        System.out.println("===================V2===================");
        visitAnnotationV2(StringRepository.class);
        visitAnnotationV2(TextRepository.class);
        System.out.println("===================V3===================");
        visitAnnotationV3(StringRepository.class);
        visitAnnotationV3(TextRepository.class);
        System.out.println("===================V4===================");
        visitAnnotationV4(StringRepository.class);
        visitAnnotationV4(TextRepository.class);
    }

    /**
     * 获取一个注解上的元注解信息，仅能获取当前层次注解，不支持两层及以上注解
     * @since Spring 2.5.6.SEC03
     *
     * @param annotationClass 注解类
     */
    private static void visitAnnotationV2(Class<? extends Annotation> annotationClass) {
        Method[] annotationAttributes = annotationClass.getMethods();
        for (Method annotationAttribute : annotationAttributes) {
            String attributeName = annotationAttribute.getName();
            Object defaultValue = annotationAttribute.getDefaultValue();
            System.out.printf("%s : %s%n", attributeName, defaultValue);
        }

        Annotation[] metaAnnotations = annotationClass.getAnnotations();
        Set<String> metaAnnotationTypeNames = new HashSet<>();
        for (Annotation metaAnnotation : metaAnnotations) {
            String metaAnnotationTypeName = metaAnnotation.annotationType().getName();
            metaAnnotationTypeNames.add(metaAnnotationTypeName);
        }

        System.out.println(metaAnnotationTypeNames);
    }

    /**
     * 获取一个注解上所有的元注解信息，仅支持两层元注解，不支持三层元注解
     * <p>
     * 支持解析{@link Component}派生的注解，派生层次为3层。
     * <p> @Component
     *     | - @Repository
     *         | - @StringRepository (可以解析{@link Component})
     *             | - @TextRepository (无法解析{@link Component})
     * @since Spring 3.0.0.RELEASE
     *
     * @param annotationClass 注解类
     */
    private static void visitAnnotationV3(Class<? extends Annotation> annotationClass) {
        Method[] annotationAttributes = annotationClass.getMethods();
        for (Method annotationAttribute : annotationAttributes) {
            String attributeName = annotationAttribute.getName();
            Object defaultValue = annotationAttribute.getDefaultValue();
            System.out.printf("%s : %s%n", attributeName, defaultValue);
        }

        System.out.println("-----------------------------------");
        Annotation[] metaAnnotations = annotationClass.getAnnotations();
        Set<String> metaAnnotationTypeNames = new HashSet<>();
        for (Annotation metaAnnotation : metaAnnotations) {
            String metaAnnotationTypeName = metaAnnotation.annotationType().getName();
            metaAnnotationTypeNames.add(metaAnnotationTypeName);

            Annotation[] metaMetaAnnotations = metaAnnotation.annotationType().getAnnotations();
            for (Annotation metaMetaAnnotation : metaMetaAnnotations) {
                String metaMetaAnnotationTypeName = metaMetaAnnotation.annotationType().getName();
                System.out.printf("%s : %s%n", metaAnnotationTypeName, metaMetaAnnotationTypeName);
                metaAnnotationTypeNames.add(metaMetaAnnotationTypeName);
            }
        }

        System.out.println("-----------------------------------");
        System.out.println(metaAnnotationTypeNames);
    }

    /**
     * 获取一个注解上所有的元注解，支持递归查找所有层次的元注解
     * @since Spring 4.0.0.RELEASE
     *
     * @param annotationClass 注解类
     */
    private static void visitAnnotationV4(Class<? extends Annotation> annotationClass) {
        Set<String> metaAnnotationTypeNames = new HashSet<>();
        Annotation[] metaAnnotations = annotationClass.getAnnotations();
        for (Annotation metaAnnotation : metaAnnotations) {
            recursivelyCollectMetaAnnotations(metaAnnotationTypeNames, metaAnnotation);
        }
        System.out.println(metaAnnotationTypeNames);
    }

    private static void recursivelyCollectMetaAnnotations(Set<String> visited, Annotation annotation) {
        if (visited.add(annotation.annotationType().getName())) {
            if (Modifier.isPublic(annotation.annotationType().getModifiers())) {
                Annotation[] metaAnnotations = annotation.annotationType().getAnnotations();
                for (Annotation metaAnnotation : metaAnnotations) {
                    recursivelyCollectMetaAnnotations(visited, metaAnnotation);
                }
            }
        }
    }

}
