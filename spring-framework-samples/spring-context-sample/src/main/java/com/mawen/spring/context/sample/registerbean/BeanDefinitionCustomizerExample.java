package com.mawen.spring.context.sample.registerbean;

import com.mawen.spring.context.sample.registerbean.service.LogService;
import com.mawen.spring.context.sample.registerbean.service.OrderService;
import org.springframework.beans.factory.config.BeanDefinitionCustomizer;
import org.springframework.context.support.GenericApplicationContext;

import java.util.Arrays;

/**
 * 基于{@link BeanDefinitionCustomizer}注册Bean
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
public class BeanDefinitionCustomizerExample {

    public static void main(String[] args) {
        GenericApplicationContext context = new GenericApplicationContext();
        context.registerBean(OrderService.OrderServiceImpl.class);
        context.registerBean(LogService.LogServiceImpl.class);
        context.refresh();
        System.out.println("Context refreshed");

        System.out.println("OrderService BeanDefinition exists :" + context.containsBeanDefinition("orderService"));
        System.out.println("OrderService exists :" + context.containsBean("orderService"));
        System.out.println("LogService BeanDefinition exists :" + context.containsBeanDefinition("logService"));
        System.out.println("LogService exists :" + context.containsBean("logService"));

        System.out.println(Arrays.toString(context.getBeanNamesForType(OrderService.class)));
        System.out.println(Arrays.toString(context.getBeanNamesForType(LogService.class)));

        OrderService orderService = context.getBean(OrderService.class);
        orderService.placeOrder("Laptop", 2);

        System.out.println("-----------");
        orderService = context.getBean(OrderService.class);
        orderService.placeOrder("Desktop", 2);

        context.close();
    }

}
