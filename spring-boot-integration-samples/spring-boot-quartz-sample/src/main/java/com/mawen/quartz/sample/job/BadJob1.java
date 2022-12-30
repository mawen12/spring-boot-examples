package com.mawen.quartz.sample.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 任务执行时抛出异常
 *
 * @author mawen
 * @since 2022/12/30
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class BadJob1 implements Job {

    private final static Logger log = LoggerFactory.getLogger(BadJob1.class);

    private int calculation;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        JobDataMap dataMap = context.getJobDetail().getJobDataMap();

        int denominator = dataMap.getInt("denominator");
        log.info("---{} executing at {} with denominator {}", jobKey, new Date(), denominator);

        // 任务首次执行时会出现异常，分母为0
        try {
            calculation = 4815 / denominator;
        } catch (Exception e) {
            log.info("--- Error in job!");
            JobExecutionException e2 = new JobExecutionException(e);

            dataMap.put("denominator", "1");

            // 任务将被立刻重新触发
            e2.setRefireImmediately(true);
            throw e2;
        }

        log.info("---{} completed at {}", jobKey, new Date());
    }
}
