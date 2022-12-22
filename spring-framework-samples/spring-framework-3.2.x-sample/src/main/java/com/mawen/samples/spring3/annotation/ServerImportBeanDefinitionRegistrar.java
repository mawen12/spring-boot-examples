package com.mawen.samples.spring3.annotation;

import com.mawen.samples.spring3.server.Server;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import java.util.stream.Stream;

/**
 * {@link Server} {@link ImportBeanDefinitionRegistrar} 实现
 */
public class ServerImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        // 复用 {@link ServerImportSelector} 实现，避免重复劳动
        ServerImportSelector importSelector = new ServerImportSelector();
        // 刷选 Class 名称集合
        String[] selectedClassNames = importSelector.selectImports(annotationMetadata);

        // 创建 Bean 定义
        Stream.of(selectedClassNames)
                .map(BeanDefinitionBuilder::genericBeanDefinition) // 转化为 BeanDefinitionBuilder 对象
                .map(BeanDefinitionBuilder::getBeanDefinition) // 转化为 BeanDefinition
                .forEach(beanDefinition ->
                        // 注册 BeanDefinition 到 BeanDefinitionRegistry
                        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, beanDefinitionRegistry)
                );
    }
}
