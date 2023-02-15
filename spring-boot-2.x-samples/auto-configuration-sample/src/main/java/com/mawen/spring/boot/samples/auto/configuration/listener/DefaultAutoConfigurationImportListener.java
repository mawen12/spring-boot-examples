package com.mawen.spring.boot.samples.auto.configuration.listener;

import org.springframework.boot.autoconfigure.AutoConfigurationImportEvent;
import org.springframework.boot.autoconfigure.AutoConfigurationImportListener;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.Set;

/**
 * 激活自动装配引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/14
 */
public class DefaultAutoConfigurationImportListener
    implements AutoConfigurationImportListener {

    @Override
    public void onAutoConfigurationImportEvent(AutoConfigurationImportEvent event) {
        // 获取当前的 ClassLoader
        ClassLoader classLoader = event.getClass().getClassLoader();
        // 候选的自动装配 Class 名单
        List<String> candidates = SpringFactoriesLoader.loadFactoryNames(EnableAutoConfiguration.class, classLoader);
        // 实际的自动装配 Class 名单
        List<String> configurations = event.getCandidateConfigurations();
        // 排除的自动装配 Class 名单
        Set<String> exclusions = event.getExclusions();
        // 输出各自的数量
        System.out.printf("自动装配 Class 名单 - 候选数量: %d, 实际数量: %d, 排除数量: %d\n",
                candidates.size(), configurations.size(), exclusions.size());
        // 输出实际和排除的自动装配 Class 名单
        System.out.println("实际的自动装配 Class 名单: ");
        configurations.forEach(System.out::println);
        System.out.println("排除的自动装配 Class 名单: ");
        exclusions.forEach(System.out::println);
    }
}
