package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.SimpleRecoveryJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 生成大量任务
 *
 * @author mawen
 * @since 2022/12/30
 */
public class LoadExample {

    private static final Logger log = LoggerFactory.getLogger(LoadExample.class);

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        scheduler.clear();

        JobDetail job = JobBuilder.newJob(SimpleRecoveryJob.class)
                .withIdentity("job1", "group1")
                .requestRecovery()
                .build();

        long timeDelay = (long) (Math.random() * 2500);
        job.getJobDataMap().put(SimpleRecoveryJob.DELAY_TIME, timeDelay);

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .build();

        scheduler.scheduleJob(job, trigger);

        scheduler.start();

        Thread.sleep(300L * 1000L);

        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
