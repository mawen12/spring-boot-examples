package com.mawen.elasticsearch.sample.java.document;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

/**
 * 批次操作示例，支持进行批量的索引、更新、删除的操作
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-bulk.html">Bulk API</a>
 * - 仅支持 JSON 或 SMILE 编码对的文档
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/7
 */
public class BulkRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        BulkRequest bulkRequest = new BulkRequest("asset", "doc");
        bulkRequest.add(
                new IndexRequest("asset", "_doc", "2")
                        .source(XContentType.JSON, "id", "2")
        );
        bulkRequest.add(
                new IndexRequest("asset", "_doc", "3")
                        .source(XContentType.JSON, "id", "3")
        );
        bulkRequest.add(
                new IndexRequest("asset", "_doc", "4")
                        .source(XContentType.JSON, "id", "4")
        );

        bulkRequest.add(
                new DeleteRequest("asset", "_doc", "2")
        );
        bulkRequest.add(
                new DeleteRequest("asset", "_doc", "3")
        );
        bulkRequest.add(
                new UpdateRequest("asset", "_doc", "4")
                        .doc("chineseName", "bulk request")
        );

        // 同步调用
        try {
            BulkResponse response = client.bulk(bulkRequest, RequestOptions.DEFAULT);
            ResponseParserUtil.parseResponse(response);
        } catch (ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT) { // 版本冲突
                String index = e.getIndex().getName();
                System.out.printf("%s 文档批量操作失败，文档版本冲突: %s", index, e.getMessage());
            } else {
                System.out.printf("文档批量操作失败，失败原因: %s", e.getMessage());
            }
        }

        client.close();
    }

}
