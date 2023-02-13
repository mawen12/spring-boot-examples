package com.mawen.rocketmq.sample.java;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.Arrays;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/13
 */
public class ConsumerExample {

    public static void main(String[] args) throws MQClientException, InterruptedException {
        // 启动消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(ProducerExample.GROUP);
        consumer.setNamesrvAddr("localhost:9876");
        consumer.subscribe(ProducerExample.TOPIC, "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (list, consumeConcurrentlyContext) -> {
            for (MessageExt message : list) {
                System.out.println(new String(message.getBody()));
            }

            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });

        consumer.start();

        Thread.sleep(30 * 1000L);

        consumer.shutdown();
    }



}
