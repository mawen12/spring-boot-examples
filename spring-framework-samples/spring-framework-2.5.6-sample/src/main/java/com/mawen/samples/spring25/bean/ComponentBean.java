package com.mawen.samples.spring25.bean;

import com.mawen.samples.spring25.annotation.StringRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * {@link @Component} Bean
 *
 */

@Component
@Qualifier
public class ComponentBean {
}
