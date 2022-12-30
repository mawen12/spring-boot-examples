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
public class BadJob2 implements Job {

    private final static Logger log = LoggerFactory.getLogger(BadJob2.class);

    private int calculation;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("---{} executing at {}", jobKey, new Date());

        try {
            int zero = 0;
            calculation = 4815 / zero;
        } catch (Exception e) {
            log.info("--- Error in job!");
            JobExecutionException e2 = new JobExecutionException(e);

            // Quartz 将会自动停止调度，所有关联当前任务的触发器将不再运行
            e2.setUnscheduleAllTriggers(true);
            throw e2;
        }

        log.info("---{} completed at {}", jobKey, new Date());
    }
}
