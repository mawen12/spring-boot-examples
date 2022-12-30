package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 使用 Cron 触发器来演示 Quartz 的调度功能
 *
 * @author mawen
 * @since 2022/12/30
 */
public class CronTriggerExample {
    private final static Logger log = LoggerFactory.getLogger(CronTriggerExample.class);
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建调度器
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        // job1 每20s执行一次
        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0/20 * * * * ?"))
                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at {} and repeat based on expression: {}", job.getKey(), ft, trigger.getCronExpression());

        // job2 每2分钟过15s执行一次
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job2", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("15 0/2 * * * ?"))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at {} and repeat based on epxression: {}", job.getKey(), ft, trigger.getCronExpression());

        // job3 在上午8点到下午5点之间，每2分钟执行一次
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job3", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/2 8-17 * * ?"))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at {} and repeat based on epxression: {}", job.getKey(), ft, trigger.getCronExpression());

        // job4 在下午5点到下午11点之间，每3分钟执行一次
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job4", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger4", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 17-23 * * ?"))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at {} and repeat based on epxression: {}", job.getKey(), ft, trigger.getCronExpression());

        // job5 每个月的第一天和第15天的上午10点运行
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job5", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger5", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 10 1,15 * ?"))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at {} and repeat based on epxression: {}", job.getKey(), ft, trigger.getCronExpression());

        // job6 只在工作日每30s运行一次
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job6", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger6", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * MON-FRI"))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at {} and repeat based on epxression: {}", job.getKey(), ft, trigger.getCronExpression());

        // job7 只在周末每30s运行一次
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job7", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0,30 * * ? * SAT,SUN"))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} has been scheduled to run at {} and repeat based on epxression: {}", job.getKey(), ft, trigger.getCronExpression());

        scheduler.start();
        Thread.sleep(300L * 1000L);
        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
