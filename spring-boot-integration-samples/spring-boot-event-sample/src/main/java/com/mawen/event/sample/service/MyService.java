package com.mawen.event.sample.service;

import com.mawen.event.sample.event.MyEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @author mawen
 * @since 2023/1/5
 */
@Service
public class MyService {

//    @Async
//    @EventListener(MyEvent.class)
    public void asyncHandleMyEvent(MyEvent event) {
        System.out.println("Async Event isSynchronizationActive: " + TransactionSynchronizationManager.isSynchronizationActive());
        System.out.println("Async Event isCurrentTransactionReadOnly: " + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        System.out.println("Async Event isActualTransactionActive: " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Async Event getCurrentTransactionName: " + TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println("Async Event CurrentTransactionIsolationLevel: " + TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
        System.out.println("Async Event ResourceMap: " + TransactionSynchronizationManager.getResourceMap());

        System.out.println(event.getMsg());

        throw new RuntimeException("This is a exception.");
    }

    /**
     * {@link TransactionalEventListener}支持配合{@link Async}异步调用，在调用前，会查看之前事务是否满足{@link TransactionalEventListener#phase()}的条件
     *
     * @param event 事件
     */
//    @Order(0)
//    @Async
//    @TransactionalEventListener(value = MyEvent.class, phase = TransactionPhase.AFTER_COMMIT)
    public void handleMyEventTransactional(MyEvent event) {System.out.println("Transactional Event isSynchronizationActive: " + TransactionSynchronizationManager.isSynchronizationActive());
        System.out.printf("%s Transactional Event isCurrentTransactionReadOnly: %s%n", Thread.currentThread().getName(), TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        System.out.printf("%s Transactional Event isActualTransactionActive: %s%n", Thread.currentThread().getName(), TransactionSynchronizationManager.isActualTransactionActive());
        System.out.printf("%s Transactional Event getCurrentTransactionName: %s%n", Thread.currentThread().getName(), TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.printf("%s Transactional Event CurrentTransactionIsolationLevel: %s%n", Thread.currentThread().getName(), TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
        System.out.printf("%s Transactional Event ResourceMap: %s%n", Thread.currentThread().getName(), TransactionSynchronizationManager.getResourceMap());

        if (true) {
            throw new RuntimeException("This is a exception");
        }
        System.out.println(event.getMsg());

    }

    @Order(1)
//    @Async
    @TransactionalEventListener(value = MyEvent.class, phase = TransactionPhase.BEFORE_COMMIT)
    public void handleMyEvent(MyEvent event) {
        System.out.println("Event isSynchronizationActive: " + TransactionSynchronizationManager.isSynchronizationActive());
        System.out.println("Event isCurrentTransactionReadOnly: " + TransactionSynchronizationManager.isCurrentTransactionReadOnly());
        System.out.println("Event isActualTransactionActive: " + TransactionSynchronizationManager.isActualTransactionActive());
        System.out.println("Event getCurrentTransactionName: " + TransactionSynchronizationManager.getCurrentTransactionName());
        System.out.println("Event CurrentTransactionIsolationLevel: " + TransactionSynchronizationManager.getCurrentTransactionIsolationLevel());
        System.out.println("Event ResourceMap: " + TransactionSynchronizationManager.getResourceMap());

        System.out.println(event.getMsg());

        throw new RuntimeException("This is a exception.");
    }




}
