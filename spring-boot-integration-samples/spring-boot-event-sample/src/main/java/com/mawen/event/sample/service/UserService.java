package com.mawen.event.sample.service;

import com.mawen.event.sample.entity.UserEntity;
import com.mawen.event.sample.event.MyEvent;
import com.mawen.event.sample.event.Publisher;
import com.mawen.event.sample.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author mawen
 * @since 2023/1/5
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private Publisher publisher;

    private AtomicLong count = new AtomicLong(0L);

    @Transactional
    public void updateUser() {
        System.out.println("isSynchronizationActive: " + TransactionSynchronizationManager.isSynchronizationActive());
        System.out.println("isCurrentTransactionReadOnly: " + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        System.out.println("isActualTransactionActive: " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("getCurrentTransactionName: " + TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println("CurrentTransactionIsolationLevel: " + TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
        System.out.println("ResourceMap: " + TransactionSynchronizationManager.getResourceMap());

        UserEntity entity = userMapper.findById(1L);
        entity.setName(entity.getName() + count.getAndIncrement());
        userMapper.updateById(entity);

        publisher.publish(new MyEvent(this, entity.getName()));

//        if (true) {
//            throw new RuntimeException("error");
//        }
    }

    public void updateUser1() {
        System.out.println("isSynchronizationActive: " + TransactionSynchronizationManager.isSynchronizationActive());
        System.out.println("isCurrentTransactionReadOnly: " + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        System.out.println("isActualTransactionActive: " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("getCurrentTransactionName: " + TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println("CurrentTransactionIsolationLevel: " + TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
        System.out.println("ResourceMap: " + TransactionSynchronizationManager.getResourceMap());

        UserEntity entity = userMapper.findById(1L);
        entity.setName(entity.getName() + count.getAndIncrement());
        userMapper.updateById(entity);
    }
}
