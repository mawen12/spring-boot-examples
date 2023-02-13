package com.mawen.rocketmq.sample.java;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.nio.charset.StandardCharsets;

/**
 * {@link DefaultMQProducer} 示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/13
 */
public class ProducerExample {

    public static final String TOPIC = "TopicTest";
    public static final String GROUP = "My_Group";

    public static void main(String[] args) throws MQClientException, MQBrokerException, RemotingException, InterruptedException {
        // 启动生产者
        DefaultMQProducer producer = new DefaultMQProducer(GROUP);
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.setSendMsgTimeout(30 * 1000);
        producer.start();

        // 消息体
        Message msg = new Message(TOPIC,
                                  "TAG",
                                  "Hello MQ".getBytes(StandardCharsets.UTF_8)
        );

        // 发送消息
        producer.send(msg);

        // 关闭生产者
        producer.shutdown();
    }
}
