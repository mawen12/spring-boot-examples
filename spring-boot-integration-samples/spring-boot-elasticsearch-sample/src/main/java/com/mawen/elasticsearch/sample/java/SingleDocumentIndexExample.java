package com.mawen.elasticsearch.sample.java;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.mawen.elasticsearch.sample.java.model.Product;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.Date;

/**
 * 单个文档索引示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/indexing.html">Indexing</a>
 * @since 2023/1/29
 */
public class SingleDocumentIndexExample {

    public static final String USERNAME = "elastic";

    public static final String PASSWORD = "elastic";

    public static final String INDEX_NAME = "products";

    public static void main(String[] args) throws IOException {
        final CredentialsProvider credentialsProvider =
                new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY,
                                           new UsernamePasswordCredentials(USERNAME, PASSWORD));

        // 创建底层客户端
        RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(hc -> hc
                        .setDefaultCredentialsProvider(credentialsProvider))
                .build();

        // 创建传输层
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 创建 API Client
        ElasticsearchClient client = new ElasticsearchClient(transport);

        System.out.println("name : " + client.info().clusterName());

        Product product = new Product("10001", "airline", new Date(), 0.00);

        // 索引
        IndexResponse response = client.index(i -> i
                .index(INDEX_NAME)
                .id(product.getId())
                .document(product)
        );

        // 打印结果
        System.out.println("Indexed with version" + response.version());

        transport.close();
    }
}
