package com.mawen.quartz.sample.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author mawen
 * @since 2022/12/30
 */
public class SimpleRecoveryJob implements Job {

    private static final Logger log = LoggerFactory.getLogger(SimpleRecoveryJob.class);

    public static final String DELAY_TIME = "delay time";

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        if (context.isRecovering()) {
            log.info("Executing job: {} RECOVERING at {}", jobKey, new Date());
        } else {
            log.info("Executing job: {} starting at {}", jobKey, new Date());
        }

        long delayTime = context.getJobDetail().getJobDataMap().getLong(DELAY_TIME);
        try {
            Thread.sleep(delayTime);
        } catch (InterruptedException e) {

        }

        log.info("Finished Executing job: {} at {}", jobKey, new Date());
    }
}
