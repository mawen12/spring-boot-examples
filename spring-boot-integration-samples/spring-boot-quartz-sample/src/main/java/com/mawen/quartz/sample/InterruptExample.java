package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.DumbInterruptableJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Quartz 演示打断任务
 *
 * @author mawen
 * @since 2022/12/30
 */
public class InterruptExample {

    private final static Logger log = LoggerFactory.getLogger(InterruptExample.class);

    public static void main(String[] args) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        Date startTime = DateBuilder.nextGivenSecondDate(null, 5);

        JobDetail job = JobBuilder.newJob(DumbInterruptableJob.class)
                .withIdentity("job1", "group1")
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        scheduler.start();
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(7000L);
                scheduler.interrupt(job.getKey());
            } catch (Exception e) {

            }
        }

        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
