package com.mawen.samples.spring5.bean;

import com.mawen.samples.spring5.annotation.TransactionalService;

/**
 * {@link TransactionalService} Bean
 */
@TransactionalService
public class TransactionalServiceBean {

    public void save() {
        System.out.println("保存操作...");
    }
}
