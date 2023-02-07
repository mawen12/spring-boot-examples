package com.mawen.elasticsearch.sample.java.document.delete;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;

import javax.management.Query;
import java.io.IOException;

/**
 * Delete By Query 示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/current/java-rest-high-document-delete-by-query.html">Delete By Query API</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
public class DeleteByQueryRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        DeleteByQueryRequest request = new DeleteByQueryRequest("human");
        request.setConflicts("proceed");
        request.setRefresh(true);
        // todo set query

        try {
            BulkByScrollResponse bulkResponse = client.deleteByQuery(request, RequestOptions.DEFAULT);
            ResponseParserUtil.parseResponse(bulkResponse);
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
