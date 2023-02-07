package com.mawen.elasticsearch.sample.java.document;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ReindexRequest;
import org.elasticsearch.rest.RestStatus;

import java.io.IOException;

/**
 * 从一个索引复制文档到另一个文档的示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-reindex.html">Reindex API</a>
 * Reindex 只会复制文档的_source，不复制 mappings、shard counts、replicas 等
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/7
 */
public class ReindexRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        ReindexRequest request = new ReindexRequest();
        request.setSourceIndices("asset").setSourceDocTypes("_doc");
        request.setDestIndex("human").setDestDocType("_doc").setDestOpType("create");

        try {
            BulkByScrollResponse response = client.reindex(request, RequestOptions.DEFAULT);
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
