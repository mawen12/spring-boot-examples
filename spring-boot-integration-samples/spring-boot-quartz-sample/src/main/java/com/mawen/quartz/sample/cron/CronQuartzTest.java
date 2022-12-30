package com.mawen.quartz.sample.cron;

import com.mawen.quartz.sample.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author mawen
 * @since 2022/12/29
 */
public class CronQuartzTest {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建任务
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        // 创建触发器
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(CronScheduleBuilder.cronSchedule("0 * * * * ?").withMisfireHandlingInstructionDoNothing()) // 当下次执行不在有效时间范围内时，不再执行
                .startAt(Date.from(LocalDateTime.of(2022, 12, 28, 11, 32, 16).atZone(ZoneId.systemDefault()).toInstant()))
                .endAt(Date.from(LocalDateTime.of(2022, 12, 29, 11, 32, 22).atZone(ZoneId.systemDefault()).toInstant()))
                .build();

        // 创建调度器
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        scheduler.start();
        scheduler.scheduleJob(jobDetail, trigger);

        Thread.sleep(600000L);
        scheduler.shutdown();
    }
}
