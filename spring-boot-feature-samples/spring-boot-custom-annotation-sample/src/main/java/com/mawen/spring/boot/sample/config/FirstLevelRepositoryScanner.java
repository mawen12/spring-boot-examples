package com.mawen.spring.boot.sample.config;

import com.mawen.spring.boot.sample.annotation.FirstLevelRepository;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * 扫描{@link FirstLevelRepository}注解的类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/9
 */
@Component
public class FirstLevelRepositoryScanner {

    public Set<String> findAnnotatedClasses(Class<? extends Annotation> annotationType, String... packagesToScan) {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(
                false);
        provider.addIncludeFilter(new AnnotationTypeFilter(annotationType));

        Set<String> ret = new HashSet<>();

        for (String packageToScan : packagesToScan) {
            Set<BeanDefinition> beanDefs = provider.findCandidateComponents(packageToScan);
            beanDefs.stream().map(BeanDefinition::getBeanClassName).forEach(ret::add);
        }

        return ret;
    }



}
