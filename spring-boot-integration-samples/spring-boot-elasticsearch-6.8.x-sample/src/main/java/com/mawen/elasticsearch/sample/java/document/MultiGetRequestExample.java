package com.mawen.elasticsearch.sample.java.document;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.MultiGetRequest;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

/**
 * 并行执行 get 请求的示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-multi-get.html">Multi-Get API</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/7
 */
public class MultiGetRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        MultiGetRequest request = new MultiGetRequest();
        request.add(
                new MultiGetRequest.Item("asset", "_doc", "1")
                        .fetchSourceContext(FetchSourceContext.FETCH_SOURCE)
        );
        request.add(
                new MultiGetRequest.Item("asset", "_doc", "4")
                        .fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE)
        );
        request.add(
                new MultiGetRequest.Item("asset", "_doc", "5")
                        .fetchSourceContext(
                                new FetchSourceContext(true, new String[]{"id", "chineseName"}, Strings.EMPTY_ARRAY)
                        )
        );

        try {
            MultiGetResponse response = client.mget(request, RequestOptions.DEFAULT);
            ResponseParserUtil.parseResponse(response);
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
