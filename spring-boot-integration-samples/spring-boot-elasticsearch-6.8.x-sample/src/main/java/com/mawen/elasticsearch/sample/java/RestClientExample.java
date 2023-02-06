package com.mawen.elasticsearch.sample.java;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * Java Low Level Client 初始化
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-low-usage-initialization.html">Initialization</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
public class RestClientExample {

    public static void main(String[] args) throws IOException {
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();
        restClient.close();
    }
}
