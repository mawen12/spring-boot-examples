package com.mawen.quartz.sample.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 用于单元测试目的的简单任务
 *
 * @author mawen
 * @since 2022/12/30
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class StatefulDumbJob implements Job {

    private final static Logger log = LoggerFactory.getLogger(StatefulDumbJob.class);

    public static final String NUM_EXECUTIONS = "NumExecutions";

    public static final String EXECUTION_DELAY = "ExecutionDelay";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("---{} execution.[{}]", context.getJobDetail().getKey(), new Date());

        JobDataMap map = context.getJobDetail().getJobDataMap();
        int executeCount = 0;
        if (map.containsKey(NUM_EXECUTIONS)) {
            executeCount = map.getInt(NUM_EXECUTIONS);
        }

        executeCount++;

        map.put(NUM_EXECUTIONS, executeCount);

        long delay = 5000L;
        if (map.containsKey(EXECUTION_DELAY)) {
            delay = map.getLong(EXECUTION_DELAY);
        }

        try {
            Thread.sleep(delay);
        } catch (InterruptedException e) {

        }

        log.info(" -{} complete ({})", context.getJobDetail().getKey(), executeCount);
    }
}
