package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.SimpleJob3;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 *
 * @author mawen
 * @since 2022/12/30
 */
public class PluginExample {

    private static final Logger log = LoggerFactory.getLogger(PluginExample.class);

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        JobDetail job = JobBuilder.newJob(SimpleJob3.class)
                .withIdentity("job3")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3")
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
