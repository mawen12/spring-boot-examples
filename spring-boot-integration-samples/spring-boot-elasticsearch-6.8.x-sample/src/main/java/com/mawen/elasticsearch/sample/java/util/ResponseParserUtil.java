package com.mawen.elasticsearch.sample.java.util;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.core.TermVectorsResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.ScrollableHitSource;

import java.util.List;

/**
 *
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
public final class ResponseParserUtil {

    public static void parseResponse(IndexResponse indexResponse) {
        String index = indexResponse.getIndex();
        String type = indexResponse.getType();
        String id = indexResponse.getId();
        long version = indexResponse.getVersion();
        if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.printf("%s/%s/%s(%d) 文档创建成功\n", index, type, id, version);
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.printf("%s/%s/%s(%d) 文档更新成功\n", index, type, id, version);
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.printf("%s/%s/%s(%d) 文档未在全部分片(%d/%d)更新成功\n", index, type, id, version, shardInfo.getSuccessful(), shardInfo.getTotal());
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.printf("%s/%s/%s(%d) 文档更新失败，失败原因: %s\n", index, type, id, version, reason);
            }
        }
    }

    public static void parseResponse(GetResponse getResponse) {
        String index = getResponse.getIndex();
        String type = getResponse.getType();
        String id = getResponse.getId();
        if (getResponse.isExists()) {
            long version = getResponse.getVersion();
            String sourceAsString = getResponse.getSourceAsString();
            System.out.printf("%s/%s/%s(%d) 文档内容为：%s.\n", index, type, id, version, sourceAsString);
        } else {
            System.out.printf("%s/%s/%s 内容未找到.\n", index, type, id);
        }
    }

    public static void parseResponse(DeleteResponse deleteResponse) {
        String index = deleteResponse.getIndex(); // 索引
        String type = deleteResponse.getType(); // 类型
        String id = deleteResponse.getId(); // id
        long version = deleteResponse.getVersion(); // 版本
        ReplicationResponse.ShardInfo shardInfo = deleteResponse.getShardInfo(); // 分片信息
        if (deleteResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) { // 待删除的文档不存在
            System.out.printf("%s/%s/%s(%d) 文档不存在\n", index, type, id, version);
        } else if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            System.out.printf("%s/%s/%s(%d) 文档删除成功\n", index, type, id, version);
        }
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) { // 删除是否陈工
            System.out.printf("%s/%s/%s(%d) 文档未在全部分片(%d/%d)删除成功\n", index, type, id, version, shardInfo.getSuccessful(), shardInfo.getTotal());
        }
        if (shardInfo.getFailed() > 0) { // 删除失败信息
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.printf("%s/%s/%s(%d) 文档删除失败，失败原因: %s\n", index, type, id, version, reason);
            }
        }
    }

    public static void parseResponse(BulkByScrollResponse bulkResponse) {
        TimeValue timeTaken = bulkResponse.getTook(); // 执行累计耗时
        boolean timedOut = bulkResponse.isTimedOut(); // 请求是否超时
        long totalDocs = bulkResponse.getTotal(); // 处理文档总数
        long deletedDocs = bulkResponse.getDeleted(); // 删除文档数量
        int batches = bulkResponse.getBatches(); // 执行的批次数
        long noops = bulkResponse.getNoops(); // 跳过的文档数
        long versionConflicts = bulkResponse.getVersionConflicts(); // 版本冲突数量
        long bulkRetries = bulkResponse.getBulkRetries(); // 请求必须重试批量索引的操作数
        long searchRetries = bulkResponse.getSearchRetries(); // 请求必须重试搜索操作的次数
        TimeValue throttledMillis = bulkResponse.getStatus().getThrottled(); // 此请求自身节流的总时间，如果当前正在休眠，不包括当前节流时间
        TimeValue throttledUntilMillis = bulkResponse.getStatus().getThrottledUntil(); // 当前任何节流睡眠的剩余时间，如果没有睡眠则为0
        List<ScrollableHitSource.SearchFailure> searchFailures = bulkResponse.getSearchFailures(); // 搜索阶段的失败
        List<BulkItemResponse.Failure> bulkFailures = bulkResponse.getBulkFailures(); // 批量索引操作中错误

        System.out.printf("总耗时: %s, 请求是否超时: %b, 处理文档总数: %d, 删除文档数: %d, 执行的批次数: %d, 跳过的文档数: %d, 版本冲突数量: %d, 请求必须重试批量索引的操作数: %d, 请求必须重试搜索操作的次数: %d, 此请求自身节流的总时间: %s, 当前节流睡眠的剩余时间: %s. \n",
                          timeTaken, timedOut, totalDocs, deletedDocs, batches, noops, versionConflicts, bulkRetries, searchRetries, throttledMillis, throttledUntilMillis);
        if (CollectionUtils.isNotEmpty(searchFailures)) {
            for (ScrollableHitSource.SearchFailure failure : searchFailures) {
                Throwable reason = failure.getReason();
                System.out.printf("执行异常：%s", reason.getMessage());
            }
        }

        if (CollectionUtils.isNotEmpty(bulkFailures)) {
            for (BulkItemResponse.Failure bulkFailure : bulkFailures) {
                Exception cause = bulkFailure.getCause();
                System.out.printf("执行异常: %s\n", cause.getMessage());
            }
        }
    }

    public static void parseResponse(UpdateResponse updateResponse) {
        String index = updateResponse.getIndex();
        String type = updateResponse.getType();
        String id = updateResponse.getId();
        long version = updateResponse.getVersion();
        if (updateResponse.getResult() == DocWriteResponse.Result.CREATED) {
            System.out.printf("%s/%s/%s(%d) 文档创建成功\n", index, type, id, version);
        } else if (updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.printf("%s/%s/%s(%d) 文档更新成功\n", index, type, id, version);
        } else if (updateResponse.getResult() == DocWriteResponse.Result.DELETED) {
            System.out.printf("%s/%s/%s(%d) 文档删除成功\n", index, type, id, version);
        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOOP) {
            System.out.printf("%s/%s/%s(%d) 文档未进行任何更新\n", index, type, id, version);
        } else if (updateResponse.getResult() == DocWriteResponse.Result.NOT_FOUND) {
            System.out.printf("%s/%s/%s(%d) 文档不存在\n", index, type, id, version);
        }
    }

    public static void parseResponse(TermVectorsResponse response) {
        String index = response.getIndex();
        String type = response.getType();
        String id = response.getId();
        boolean found = response.getFound();
        System.out.printf("%s/%s/%s 是否查找到：%s\n", index, type, id, found);

        for (TermVectorsResponse.TermVector termVector : response.getTermVectorsList()) {
            String fieldName = termVector.getFieldName();
            int docCount = termVector.getFieldStatistics().getDocCount();
            long sumTotalTermFreq = termVector.getFieldStatistics().getSumTotalTermFreq();
            long sumDocFreq = termVector.getFieldStatistics().getSumDocFreq();
            System.out.printf("字段：%s, 总数：%d, 总词频之和: %d, 文档词频之和：%d.\n", fieldName, docCount, sumTotalTermFreq, sumDocFreq);

            if (termVector.getTerms() != null) {
                for (TermVectorsResponse.TermVector.Term term : termVector.getTerms()) {
                    String termStr = term.getTerm();
                    int termFreq = term.getTermFreq();
                    Integer docFreq = term.getDocFreq();
                    Long totalTermFreq = term.getTotalTermFreq();
                    Float score = term.getScore();
                    System.out.printf("当前术语的名称：%s, 术语的频率: %d, 术语的文档频率: %d, 术语的总词频： %d, 术语的得分: %f.\n",
                                      termStr, termFreq, docFreq, totalTermFreq, score);
                    if (term.getTokens() != null) {
                        for (TermVectorsResponse.TermVector.Token token : term.getTokens()) {
                            Integer position = token.getPosition();
                            Integer startOffset = token.getStartOffset();
                            Integer endOffset = token.getEndOffset();
                            String payload = token.getPayload();
                            System.out.printf("术语的位置：%d, 术语的开始偏移量: %d, 术语的结束的偏移量：%d, 术语的载荷: %d.\n",
                                              position, startOffset, endOffset, payload);
                        }
                    }
                }
            }
        }


    }

    public static void parseResponse(BulkResponse response) {
        for (BulkItemResponse bulkItemResponse : response) {
            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
            switch (bulkItemResponse.getOpType()) {
                case INDEX:
                case CREATE:
                    IndexResponse indexResponse = (IndexResponse) itemResponse;
                    parseResponse(indexResponse);
                    break;
                case UPDATE:
                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
                    parseResponse(updateResponse);
                    break;
                case DELETE:
                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
                    parseResponse(deleteResponse);
                    break;
            }
        }
        if (response.hasFailures()) {
            for (BulkItemResponse bulkItemResponse : response) {
                if (bulkItemResponse.isFailed()) {
                    BulkItemResponse.Failure failure = bulkItemResponse.getFailure();
                    System.out.printf("操作失败：%s\n", failure.getCause().getMessage());
                }
            }
        }
    }

    public static void parseResponse(MultiGetResponse response) {
        for (MultiGetItemResponse multiGetItemResponse : response) {
            if (multiGetItemResponse.isFailed()) {
                MultiGetResponse.Failure failure = multiGetItemResponse.getFailure();
                System.out.printf("操作失败：%s\n", failure.getFailure().getMessage());
            }
            GetResponse getResponse = multiGetItemResponse.getResponse();
            parseResponse(getResponse);
        }
    }
}
