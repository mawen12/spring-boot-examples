package com.mawen.spring.context.sample.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 订单服务
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
public interface OrderService {

    void placeOrder(String item, Integer qty);

    public static class OrderServiceImpl implements OrderService {

        private LogService logService;

        public OrderServiceImpl() {
            System.out.printf("instance of %s created: %s%n",
                              this.getClass().getName(),
                              System.identityHashCode(this));
        }

        public OrderServiceImpl(LogService logService) {
            this();
            this.logService = logService;
        }

        public void setLogService(LogService logService) {
            this.logService = logService;
        }

        @Override
        public void placeOrder(String item, Integer qty) {
            System.out.printf("placing order item: %s, qty: %s, instance: %s%n",
                              item, qty, System.identityHashCode(this));
            if (logService != null) {
                logService.log("Order placed");
            }
        }

        private void init() {
            System.out.printf("%s, init method called: %s%n",
                              this.getClass().getName(),
                              System.identityHashCode(this));
        }

        private void destroy() {
            System.out.printf("%s, destroy method called: %s%n",
                              this.getClass().getName(),
                              System.identityHashCode(this));
        }
    }


}
