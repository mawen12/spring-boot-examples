package com.mawen.elasticsearch.sample.java;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.mawen.elasticsearch.sample.java.model.Product;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * 通过Id获取文档示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/reading.html">Reading</a>
 * @since 2023/1/30
 */
public class ReadDocumentByIdExample {

    public static final String USERNAME = "elastic";

    public static final String PASSWORD = "elastic";

    public static final String INDEX_NAME = "products";


    public static void main(String[] args) throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));
        // 创建底层客户端
        RestClient restClient = RestClient.builder(
                        new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(hc -> hc
                        .setDefaultCredentialsProvider(credentialsProvider))
                .build();
        // 创建传输层
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());
        // 创建 Java 客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);
        // 获取id对应的记录，转换成对象
        GetResponse<Product> response = client.get(g -> g
                                                           .index(INDEX_NAME)
                                                           .id("10001"),
                                                   Product.class);
        if (response.found()) {
            Product product = response.source();
            System.out.println("Product name " + product.getName());
        } else {
            System.err.println("Product not found");
        }

        // 获取id对应的记录，使用raw json
        GetResponse<ObjectNode> response1 = client.get(g -> g
                                                                           .index(INDEX_NAME)
                                                                           .id("10002"),
                                                                   ObjectNode.class);
        if (response1.found()) {
            ObjectNode json = response1.source();
            String name = json.get("name").asText();
            System.out.println("Product name " + name);
        } else {
            System.err.println("Product not found");
        }

        transport.close();
    }

}
