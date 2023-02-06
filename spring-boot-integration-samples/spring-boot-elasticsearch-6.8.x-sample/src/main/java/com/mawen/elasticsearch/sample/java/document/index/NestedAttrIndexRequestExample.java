package com.mawen.elasticsearch.sample.java.document.index;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.mawen.elasticsearch.sample.java.model.Cat;
import com.mawen.elasticsearch.sample.java.model.Human;
import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

/**
 * Nested 类型属性索引示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @see <a href="file:spring-boot-elasticsearch-6.8.x-sample/src/main/resources/asset.edql">/resources/asset.edql</a>
 * @since 2023/2/6
 */
public class NestedAttrIndexRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        Human human = Human
                .builder()
                .id(1L)
                .name("Jack")
                .cats(Lists.newArrayList(
                        Cat.builder()
                                .colors(1)
                                .name("Iriba")
                                .breed("European Shorthair")
                                .build(),
                        Cat.builder()
                                .colors(2)
                                .name("Phoebe")
                                .breed("European")
                                .build(),
                        Cat.builder()
                                .colors(3)
                                .name("Nino")
                                .breed("Aegean")
                                .build()
                ))
                .build();


        IndexRequest indexRequest = new IndexRequest("human", "_doc", String.valueOf(human.getId()))
                .source(JSON.toJSONString(human), XContentType.JSON);

        try {
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            ResponseParserUtil.parseResponse(indexResponse);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                String index = e.getIndex().getName();
                System.out.printf("%s 文档创建失败，文档已存在: %s", index, e.getMessage());
            } else {
                System.out.printf("文档创建失败，失败原因: %s", e.getMessage());
            }
        }

        client.close();
    }

}
