package com.mawen.elasticsearch.sample.java.document;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.TermVectorsRequest;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

/**
 * 词向量示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-term-vectors.html">Term Vectors API</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/7
 */
public class TermVectorsRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        TermVectorsRequest request = new TermVectorsRequest("asset", "_doc", "1");
        request.setFields("chineseName");

        try {
            TermVectorsResponse response = client.termvectors(request, RequestOptions.DEFAULT);
            ResponseParserUtil.parseResponse(response);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) { // 版本冲突
                String index = e.getIndex().getName();
                System.out.printf("%s 文档更新操作失败，文档版本冲突: %s", index, e.getMessage());
            } else {
                System.out.printf("文档更新失败，失败原因: %s", e.getMessage());
            }
        }

        client.close();
    }

}
