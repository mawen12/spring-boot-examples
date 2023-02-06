package com.mawen.elasticsearch.sample.java;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;

import java.io.IOException;

/**
 * Rest High Level Client 初始化
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-getting-started-initialization.html">Initialization</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
public class RestHighLevelClientExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        client.close();
    }

}
