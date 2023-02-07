package com.mawen.elasticsearch.sample.java.document.index;

import com.alibaba.fastjson2.JSON;
import com.google.common.collect.Lists;
import com.mawen.elasticsearch.sample.java.model.Asset;
import com.mawen.elasticsearch.sample.java.model.AssetAttr;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.mawen.elasticsearch.sample.java.util.ResponseParserUtil.parseResponse;

/**
 * Index API - Index Request
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-index.html">Index API - Index Request</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
public class IndexRequestExample {

    public static void main(String[] args) throws IOException {
        // 初始化客户端
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        // 索引请求
        IndexRequest request = new IndexRequest(
                "asset", // 索引
                "_doc", // 类型
                "1" // 文档Id
        );

        // 1.字符串标识的文档源
        String jsonString = JSON.toJSONString(Asset.builder()
                                                      .id(1L)
                                                      .chineseName("码值")
                                                      .extendedAttrs(Lists.newArrayList(
                                                              AssetAttr.builder().keyCode("comt").keyLabel("备注").valueId(
                                                                      "目标单词ID").valueLabel("目标单词ID").build(),
                                                              AssetAttr.builder().keyCode("col_sorting_seq_num").keyLabel(
                                                                      "字段排序序号").valueId("2").valueLabel("2").build()
                                                      ))
                                                      .build());
        request.source(jsonString, XContentType.JSON);

        // 2.Map结构的文档源
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("user", "jack");
        jsonMap.put("postDate", "2013-01-30");
        jsonMap.put("message", "Hello Elasticsearch");

        IndexRequest request1 = new IndexRequest(
                "posts",
                "doc",
                "2"
        ).source(jsonMap);

        // 3.XContentFactory 标识的文档源
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("user", "Lucy");
            builder.timeField("postDate", new Date());
            builder.field("message", "Hello World!");
        }
        builder.endObject();

        IndexRequest request2 = new IndexRequest("posts", "doc", "3")
                .source(builder);

        // 4.键值对的文档源
        IndexRequest request3 = new IndexRequest("posts", "doc", "4")
                .source("user", "bill",
                        "postDate", new Date(),
                        "message", "Hello");

        // 同步执行
        try {
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            parseResponse(indexResponse);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) {
                String index = e.getIndex().getName();
                System.out.printf("%s/%s/%s 文档创建失败，文档已存在: %s", request.index(), request.type(), request.id(), e.getMessage());
            } else {
                System.out.printf("%s/%s/%s 文档创建失败，失败原因: %s", request.index(), request.type(), request.id(), e.getMessage());

            }
        }
        client.close();
    }
}
