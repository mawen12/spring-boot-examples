package com.mawen.elasticsearch.sample.java;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.shutdown.ElasticsearchShutdownClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import com.mawen.elasticsearch.sample.java.model.Product;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;

import java.io.IOException;

/**
 * Elasticsearch Java API Client 示例
 *
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/connecting.html">Connecting</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/29
 */
public class JavaApiClientExample {

    public static final String INDEX_NAME = "products";

    public static void main(String[] args) throws IOException {
        // 创建低级的客户端
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200)).build();

        // 创建带有jackson映射器的传输层
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 创建 API 客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);

        // 搜索
        SearchResponse<Product> search = client.search(s -> s
                .index(INDEX_NAME)
                .query(q -> q
                        .term(t -> t
                                .field("name")
                                .value(v -> v.stringValue("car")))), Product.class);

        for (Hit<Product> hit : search.hits().hits()) {
            System.out.println(hit.source());
        }

        transport.close();
    }

}
