package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.ColorJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 展示 Quartz 中如何传递任务参数，如何保持任务状态
 *
 * @author mawen
 * @since 2022/12/30
 */
public class JobStateExample {

    private final static Logger log = LoggerFactory.getLogger(JobStateExample.class);

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 运行五次，运行一次，重复四次，重复间隔10s
        JobDetail job = JobBuilder.newJob(ColorJob.class)
                .withIdentity("job1", "group1")
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(4))
                .build();

        // 将参数传递到任务中
        job.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Green");
        job.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at: {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job2 运行5次，重复间隔10s
        job = JobBuilder.newJob(ColorJob.class)
                .withIdentity("job2", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(4))
                .build();

        job.getJobDataMap().put(ColorJob.FAVORITE_COLOR, "Red");
        job.getJobDataMap().put(ColorJob.EXECUTION_COUNT, 1);

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at: {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        scheduler.start();
        Thread.sleep(60L * 1000L);
        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
