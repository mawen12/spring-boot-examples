package com.mawen.elasticsearch.sample.java.util;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.replication.ReplicationResponse;
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
            System.out.printf("%s-%s-%s(%d) 文档创建成功\n", index, type, id, version);
        } else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.printf("%s-%s-%s(%d) 文档更新成功\n", index, type, id, version);
        }
        ReplicationResponse.ShardInfo shardInfo = indexResponse.getShardInfo();
        if (shardInfo.getTotal() != shardInfo.getSuccessful()) {
            System.out.printf("%s-%s-%s(%d) 文档未在全部分片(%d/%d)更新成功\n", index, type, id, version, shardInfo.getSuccessful(), shardInfo.getSuccessful());
        }
        if (shardInfo.getFailed() > 0) {
            for (ReplicationResponse.ShardInfo.Failure failure : shardInfo.getFailures()) {
                String reason = failure.reason();
                System.out.printf("%s-%s-%s(%d) 文档更新失败，失败原因: %s\n", index, type, id, version, reason);
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
                System.out.printf("执行异常: %s", cause.getMessage());
            }
        }
    }
}
