package com.mawen.elasticsearch.sample.java;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.aggregations.HistogramBucket;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;

import java.io.IOException;
import java.util.List;

/**
 * 聚合查询，将数据汇总为统计数据 示例
 *
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/aggregations.html">Aggregations</a>
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/30
 */
public class AggregationExample {

    public static final String USERNAME = "elastic";

    public static final String PASSWORD = "elastic";

    public static final String INDEX_NAME = "products";


    public static void main(String[] args) throws IOException {
        // 认证信息
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(USERNAME, PASSWORD));

        // 创建底层客户端
        RestClient restClient = RestClient.builder(new HttpHost("localhost", 9200))
                .setHttpClientConfigCallback(hc -> hc.setDefaultCredentialsProvider(credentialsProvider))
                .build();

        // 创建传输层
        RestClientTransport transport = new RestClientTransport(restClient, new JacksonJsonpMapper());

        // 创建 Java 客户端
        ElasticsearchClient client = new ElasticsearchClient(transport);

        // 搜索
        String searchText = "airline";

        Query byName = MatchQuery.of(m -> m
                        .field("name")
                        .query(searchText))
                ._toQuery();

        SearchResponse<Void> response = client.search(s -> s
                                                               .index(INDEX_NAME)
                                                               .size(0)
                                                               .query(byName)
                                                               .aggregations("price-histogram", a -> a
                                                                       .histogram(h -> h
                                                                               .field("price")
                                                                               .interval(2.0))),
                                                       Void.class);

        List<HistogramBucket> buckets = response.aggregations()
                .get("price-histogram")
                .histogram()
                .buckets().array();

        for (HistogramBucket bucket : buckets) {
            System.out.println("There are " + bucket.docCount() + " airline under " + bucket.key());
        }

        transport.close();
    }

}
