package com.mawen.elasticsearch.sample.java.document;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.apache.logging.log4j.util.Strings;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;

/**
 * 获取一个文档示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-get.html">Get API</a>
 * - 通过设置 FetchSourceContext 来分别指定：返回_source、不返回_source、返回_source部分信息
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/7
 */
public class GetRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        GetRequest request = new GetRequest("asset", "_doc", "1");
        // 1.默认启用，此时不会返回文档内容
//        request.fetchSourceContext(FetchSourceContext.DO_NOT_FETCH_SOURCE);
        // 2.获取source所有内容
//        request.fetchSourceContext();
        // 3.获取source部分信息
        request.fetchSourceContext(
                new FetchSourceContext(
                        true, // 获取source所有内容
                        Strings.EMPTY_ARRAY, // 获取所有字段
                        new String[]{"chineseName"} // 排除 chineseName 字段
                )
        );
        try {
            GetResponse response = client.get(request, RequestOptions.DEFAULT);
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
