package com.mawen.elasticsearch.sample.java.document;

import com.mawen.elasticsearch.sample.java.util.ResponseParserUtil;
import org.apache.http.HttpHost;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.script.Script;
import org.elasticsearch.script.ScriptType;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * 更新文档的示例
 * <a href="https://www.elastic.co/guide/en/elasticsearch/client/java-rest/6.8/java-rest-high-document-update.html">Update API</a>
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/7
 */
public class UpdateRequestExample {

    public static void main(String[] args) throws IOException {
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200)
                )
        );

        UpdateRequest request = new UpdateRequest("asset", "_doc", "1");

//        updateByScript(request);
//        updateByJsonString(request);
//        updateByMap(request);
//        updateByXContentBuilder(request);
//        updateByKeyValue(request);
        upsert(request);
        // 同步调用
        try {
            UpdateResponse updateResponse = client.update(request, RequestOptions.DEFAULT);
            ResponseParserUtil.parseResponse(updateResponse);
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

    /**
     * 使用脚本更新，指定脚本内容以及参数
     *
     * @param request
     */
    private static void updateByScript(UpdateRequest request) {
        Map<String, Object> parameters = Collections.singletonMap("chineseName", "script");
        Script inline = new Script(ScriptType.INLINE,
                                   "painless",
                                   "ctx._source.chineseName = params.chineseName",
                                   parameters);
        request.script(inline);
    }

    /**
     * 使用 JSON 字符串更新
     *
     * @param request
     */
    private static void updateByJsonString(UpdateRequest request) {
        String jsonString = "{" +
                "\"chineseName\": \"jsonString\"" +
                "}";
        request.doc(jsonString, XContentType.JSON);
    }

    /**
     * 使用 Map 更新
     *
     * @param request
     */
    private static void updateByMap(UpdateRequest request) {
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("chineseName", "Map");
        request.doc(jsonMap);
    }

    /**
     * 使用 XContentBuilder 更新
     *
     * @param request
     * @throws IOException
     */
    private static void updateByXContentBuilder(UpdateRequest request) throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.field("chineseName", "XContentBuilder");
        }
        builder.endObject();
        request.doc(builder);
    }

    /**
     * 使用 key-value 键值对更新
     *
     * @param request
     */
    private static void updateByKeyValue(UpdateRequest request) {
        request.doc("chineseName", "key-value-pair");
    }

    /**
     * 使用 upsert 更新，不存在则创建，存在则更新
     *
     * @param request
     */
    private static void upsert(UpdateRequest request) {
        String jsonString = "{" +
                "\"chineseName\": \"upsert\"" +
                "}";
        request.doc(jsonString, XContentType.JSON).upsert(jsonString, XContentType.JSON);
    }
}
