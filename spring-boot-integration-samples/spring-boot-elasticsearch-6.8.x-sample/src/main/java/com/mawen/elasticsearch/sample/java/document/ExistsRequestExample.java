package com.mawen.elasticsearch.sample.java.document;

import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

/**
 * 判断指定文档是否存在的示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-exists.html">Exists API</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/7
 */
public class ExistsRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

//        GetRequest request = new GetRequest("human", "_doc", "1");
        GetRequest request = new GetRequest("human");
        request.fetchSourceContext(new FetchSourceContext(false)); // 禁用获取 _source 信息
        request.storedFields("_none_"); // 禁用获取的字段

        // 同步调用
        try {
            boolean exists = client.exists(request, RequestOptions.DEFAULT);
            System.out.printf("%s/%s/%s 文档是否存在：%s", request.index(), request.type(), request.id(), exists);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) { // 版本冲突
                String index = e.getIndex().getName();
                System.out.printf("%s 文档操作失败，文档版本冲突: %s", index, e.getMessage());
            } else {
                System.out.printf("文档查询失败，失败原因: %s", e.getMessage());
            }
        }


        client.close();
    }

}
