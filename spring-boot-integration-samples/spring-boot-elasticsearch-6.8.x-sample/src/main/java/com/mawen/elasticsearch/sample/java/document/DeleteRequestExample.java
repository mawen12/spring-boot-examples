package com.mawen.elasticsearch.sample.java.document;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.support.WriteRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

/**
 * 删除文档示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-delete.html">Delete API</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
public class DeleteRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        DeleteRequest request = new DeleteRequest("human", "_doc", "1");
        request.setRefreshPolicy(WriteRequest.RefreshPolicy.IMMEDIATE);

        // 同步调用
        try {
            DeleteResponse deleteResponse = client.delete(request, RequestOptions.DEFAULT);
            ResponseParserUtil.parseResponse(deleteResponse);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) { // 版本冲突
                String index = e.getIndex().getName();
                System.out.printf("%s 文档操作失败，文档版本冲突: %s", index, e.getMessage());
            } else {
                System.out.printf("文档删除失败，失败原因: %s", e.getMessage());
            }
        }

        client.close();
    }

}
