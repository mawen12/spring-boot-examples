package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.BadJob1;
import com.mawen.quartz.sample.job.BadJob2;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * Quartz 如何处理任务抛出的 JobExecutionExceptions
 *
 * @author mawen
 * @since 2022/12/30
 */
public class JobExceptionExample {

    private final static Logger log = LoggerFactory.getLogger(JobStateExample.class);

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 每10s运行一次，首次运行抛出异常，并立刻重新触发
        JobDetail job = JobBuilder.newJob(BadJob1.class)
                .withIdentity("job1", "group1")
                .usingJobData("denominator", 0)
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .repeatForever())
                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job2 每5s运行一次，首次运行抛出异常，且不在触发
        job = JobBuilder.newJob(BadJob2.class)
                .withIdentity("job2", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(5)
                        .repeatForever())
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        scheduler.start();
        Thread.sleep(30L * 1000L);
        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
