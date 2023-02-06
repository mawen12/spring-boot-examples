package com.mawen.elasticsearch.sample.java;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.MatchQuery;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch._types.query_dsl.RangeQuery;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.SearchTemplateResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.elasticsearch.core.search.TotalHits;
import co.elastic.clients.elasticsearch.core.search.TotalHitsRelation;
import co.elastic.clients.json.JsonData;
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
import java.util.List;

/**
 * 搜索文档示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-api-client/8.5/searching.html">Searching</a>
 * @since 2023/1/30
 */
public class SearchingExample {
    public static final String USERNAME = "elastic";

    public static final String PASSWORD = "elastic";

    public static final String INDEX_NAME = "products";

    public static void main(String[] args) throws IOException {
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
        // 查询指定文本
        final String searchText = "airline";
        SearchResponse<Product> response = client.search(s -> s
                                                                 .index(INDEX_NAME)
                                                                 .query(q -> q
                                                                         .match(t -> t
                                                                                 .field("name")
                                                                                 .query(searchText))),
                                                         Product.class);
        // 获取查询数量
        TotalHits total = response.hits().total();
        boolean isExactResult = total.relation() == TotalHitsRelation.Eq;

        if (isExactResult) {
            System.out.println("There are " + total.value() + " results.");
        } else {
            System.out.println("There are more than " + total.value() + " results");
        }

        List<Hit<Product>> hits = response.hits().hits();
        for (Hit<Product> hit : hits) {
            Product product = hit.source();
            System.out.println("Found product " + product);
        }

        // 嵌套查询
        System.out.println("Nested Search ...");
        String searchText1 = "airline";
        double maxPrice = 5.00;
        // 根据产品名称检索
        Query byName = MatchQuery.of(m -> m
                        .field("name")
                        .query(searchText1))
                ._toQuery();
        // 根据最大价格检索
        Query byMaxPrice = RangeQuery.of(r -> r
                        .field("price")
                        .gte(JsonData.of(maxPrice)))
                ._toQuery();

        SearchResponse<Product> response1 = client.search(s -> s
                .index(INDEX_NAME)
                .query(q -> q
                        .bool(b -> b
                                .must(byName)
                                .must(byMaxPrice))), Product.class);

        List<Hit<Product>> hits1 = response1.hits().hits();
        for (Hit<Product> hit1 : hits1) {
            Product product = hit1.source();
            System.out.println("Found product " + product);
        }

        // 模板检索
        System.out.println("Template Search ...");
        // 创建脚本
        client.putScript(r -> r
                .id("query-script")
                .script(s -> s
                        .lang("mustache")
                        .source("{\"query\":{\"match\":{\"{{field}}\":\"{{value}}\"}}}")
                ));
        // 检索
        SearchTemplateResponse<Product> response2 = client.searchTemplate(r -> r
                                                                                  .index(INDEX_NAME)
                                                                                  .id("query-script")
                                                                                  .params("field", JsonData.of("name"))
                                                                                  .params("value", JsonData.of(searchText1)),
                                                                          Product.class);

        List<Hit<Product>> hits2 = response2.hits().hits();
        for (Hit<Product> hit : hits2) {
            Product product = hit.source();
            System.out.println("Found product " + product);
        }

        transport.close();
    }
}
