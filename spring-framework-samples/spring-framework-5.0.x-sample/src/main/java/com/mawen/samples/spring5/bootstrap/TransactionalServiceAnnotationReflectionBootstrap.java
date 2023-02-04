package com.mawen.samples.spring5.bootstrap;

import com.mawen.samples.spring5.annotation.TransactionalService;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.annotation.Annotation;
import java.lang.annotation.Target;
import java.lang.reflect.AnnotatedElement;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 获取{@link TransactionalService}属性信息
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/2
 */
@TransactionalService(name = "test")
public class TransactionalServiceAnnotationReflectionBootstrap {

    public static void main(String[] args) {
        // Class 实现了 AnnotatedElement 接口
        AnnotatedElement annotatedElement = TransactionalServiceAnnotationReflectionBootstrap.class;
        // 从 AnnotatedElement 获取 TransactionalService
        TransactionalService transactionalService = annotatedElement.getAnnotation(TransactionalService.class);
        // 显示地调用属性方法 TransactionalService#name() 获取属性
        System.out.println("call method...");
        String nameAttribute = transactionalService.name();
        System.out.println("@TransactionalService.name() = " + nameAttribute);

        // 完全 Java 反射实现（ReflectionUtils 为Spring反射工具类），打印所有的无参方法
        System.out.println("invoke method with no parameter...");
        ReflectionUtils.doWithMethods(TransactionalService.class,
                method -> System.out.printf("@TransactionalService.%s() = %s\n",
                        method.getName(),
                        ReflectionUtils.invokeMethod(method, transactionalService)), // 执行 method 反射调用
                method -> method.getParameterCount() == 0 // 选择无参数方法
        );

        // 完全 Java 反射实现（ReflectionUtils 为Spring反射工具类），打印非Annotation类中的方法
        System.out.println("invoke method not in Annotation.class...");
        ReflectionUtils.doWithMethods(TransactionalService.class,
                method -> System.out.printf("@TransactionalService.%s() = %s\n",
                        method.getName(),
                        ReflectionUtils.invokeMethod(method, transactionalService)), // 执行 method 反射调用
                method -> !method.getDeclaringClass().equals(Annotation.class) // 选择非 Annotation 方法
        );

        // 获取 transactionalService 的所有元注解
        System.out.println("invoke method in TransactionalService.class...");
        Set<Annotation> metaAnnotations = getAllMetaAnnotations(transactionalService);
        metaAnnotations.forEach(TransactionalServiceAnnotationReflectionBootstrap::printAnnotationAttribute);
    }

    /**
     * 递归获取一个注解上的所有元注解
     *
     * @param annotation 注解
     * @return 元注解集合
     */
    private static Set<Annotation> getAllMetaAnnotations(Annotation annotation) {
        Annotation[] metaAnnotations = annotation.annotationType().getAnnotations();

        if (ObjectUtils.isEmpty(metaAnnotations)) { // 没有找到，返回空集合
            return Collections.emptySet();
        }

        // 获取所有非 Java 标准元注解集合
        Set<Annotation> metaAnnotationSet = Stream.of(metaAnnotations)
                // 排除 Java 标准注解，如 @Target、@Documented 等，它们因相互依赖，将导致递归不断
                // 通过 java.lang.annotation 包名排除
                .filter(metaAnnotation -> !Target.class.getPackage().equals(metaAnnotation.annotationType().getPackage()))
                .collect(Collectors.toSet());

        // 递归查找元注解的元注解集合
        Set<Annotation> metaMetaAnnotationSet = metaAnnotationSet.stream()
                .map(TransactionalServiceAnnotationReflectionBootstrap::getAllMetaAnnotations)
                .collect(HashSet::new, Set::addAll, Set::addAll);

        // 添加递归结果
        metaAnnotationSet.addAll(metaMetaAnnotationSet);

        return metaAnnotationSet;
    }

    private static void printAnnotationAttribute(Annotation annotation) {
        Class<?> annotationType = annotation.annotationType();
        // 完全 Java 反射实现 （ReflectionUtils 为Spring反射工具类）
        ReflectionUtils.doWithMethods(annotationType,
                method -> System.out.printf("@%s.%s = %s\n",
                        annotationType.getSimpleName(),
                        method.getName(),
                        ReflectionUtils.invokeMethod(method, annotation)),
                method -> !method.getDeclaringClass().equals(Annotation.class)
        );
    }
}
