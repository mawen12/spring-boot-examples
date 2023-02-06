package com.mawen.elasticsearch.sample.java;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.BulkRequest;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkResponseItem;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.json.jsonb.JsonbJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.mawen.elasticsearch.sample.java.model.Product;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 多文档索引示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/indexing-bulk.html">Indexing bulk</a>
 * @since 2023/1/29
 */
public class MultiDocumentsIndexExample {

    public static final String USERNAME = "elastic";

    public static final String PASSWORD = "elastic";

    public static final String INDEX_NAME = "products";

    public static void main(String[] args) throws IOException {
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(hc -> hc.setDefaultCredentialsProvider(credentialsProvider))
                .build();

        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        ElasticsearchClient client = new ElasticsearchClient(transport);

        // 构建批次请求
        BulkRequest.Builder br = new BulkRequest.Builder();

        long id = 10002L;
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            products.add(new Product(String.valueOf(id++), "airline", new Date(), (double) i));
        }

        for (Product product : products) {
            br.operations(op -> op
                    .index(idx -> idx
                            .index(INDEX_NAME)
                            .id(product.getId())
                            .document(product)));
        }

        // 请求
        BulkResponse result = client.bulk(br.build());

        // 打印结果
        if (result.errors()) {
            System.out.println("Bulk had errors");
            for (BulkResponseItem item : result.items()) {
                if (item.error() != null) {
                    System.out.println(item.error().reason());
                }
            }
        }

        // 关闭连接
        transport.close();
    }
}
