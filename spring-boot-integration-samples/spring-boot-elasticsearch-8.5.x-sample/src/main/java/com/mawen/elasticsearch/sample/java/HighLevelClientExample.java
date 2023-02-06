package com.mawen.elasticsearch.sample.java;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.RestHighLevelClientBuilder;

/**
 * Elasticsearch High Level Client 示例
 *
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/migrate-hlrc.html#_compatibility_mode_using_a_7_17_client_with_elasticsearch_8_x">Migrating from the High Level Rest Client</a>
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/29
 */
public class HighLevelClientExample {

    public static final String INDEX_NAME = "products";

    public static void main(String[] args) {
        // 创建低级的客户端
        RestClient httpClient = RestClient.builder(
                new HttpHost("localhost", 9200)
        ).build();

        // 创建 HLRC
        RestHighLevelClient hlrc = new RestHighLevelClientBuilder(httpClient)
                .setApiCompatibilityMode(true)
                .build();

        // 创建带有jackson映射器的传输层
        RestClientTransport transport = new RestClientTransport(
                httpClient,
                new JacksonJsonpMapper()
        );

        // 创建 API 客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);

        // 搜索
        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX_NAME);
//        hlrc.search(searchRequest);

//        for (Hit hit : search.hits().hits()) {
//            System.out.println(hit.source());
//        }
    }

}
