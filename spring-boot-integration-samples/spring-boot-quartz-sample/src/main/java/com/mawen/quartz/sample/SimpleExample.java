package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.HelloJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @author mawen
 * @since 2022/12/29
 */
public class SimpleExample {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建任务
        JobDetail job = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();

        // 创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startNow()
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(2)
                        .repeatForever())
                .build();

        // 创建调度器
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();
        scheduler.scheduleJob(job, trigger);

        scheduler.start();
        Thread.sleep(6000L);
        scheduler.shutdown();
    }

}
