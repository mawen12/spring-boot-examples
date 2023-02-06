package com.mawen.elasticsearch.sample.java;

import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.client.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

/**
 * RestClient 示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/java-rest-low-usage-initialization.html">Low Level Rest Client</a>
 * @since 2023/1/30
 */
@Slf4j
public class RestClientExample {

    public static final String USERNAME = "elastic";

    public static final String PASSWORD = "elastic";

    public static final String INDEX_NAME = "products";

    public static void main(String[] args) throws IOException, InterruptedException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
        // 初始化
        RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(hc -> hc
                        .setDefaultCredentialsProvider(credentialsProvider)
                        .setDefaultIOReactorConfig(IOReactorConfig.custom() // 设置线程数
                                                           .setIoThreadCount(1)
                                                           .build())
                )
                .setFailureListener(new RestClient.FailureListener() {
                    @Override
                    public void onFailure(Node node) {
                        log.error("Node can not access {}.", node.getHost());
                    }
                })
                .setRequestConfigCallback(rc -> rc
                        .setConnectTimeout(5000) // 设置连接超时时间
                        .setSocketTimeout(60000)) // 设置套接字超时时间
                .build();

        // 同步请求
        Request request = new Request("GET", "/");
        request.addParameter("pretty", "true");
        Response response = restClient.performRequest(request);
        log.info("{}", response.getStatusLine().getStatusCode());

        // 异步请求
        CountDownLatch latch = new CountDownLatch(1);
        Cancellable cancellable = restClient.performRequestAsync(request, new ResponseListener() {
            @Override
            public void onSuccess(Response response) {
                log.info("Async request {}.", response.getStatusLine().getStatusCode());
                latch.countDown();
            }

            @Override
            public void onFailure(Exception exception) {
                log.error("Exception {}.", exception.getMessage());
                latch.countDown();
            }
        });

        latch.await();
        // 关闭
        restClient.close();
    }


}
