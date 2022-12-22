package com.mawen.samples.spring25.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

/**
 * Holder 类负责 {@link @Autowired} 注入 Bean
 */
@Component("nameRepositoryHolder")
public class AutowiredBeanHolder {

    @Autowired
    private Collection<NameRepository> repositories;

    @Autowired
    @Qualifier("chineseNameRepository")
    private NameRepository nameRepository;

    public Collection<NameRepository> getRepositories() {
        return Collections.unmodifiableCollection(repositories);
    }

}
