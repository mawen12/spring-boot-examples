package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author mawen
 * @since 2022/12/29
 */
public class SimpleTriggerExample {

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        // 创建调度器
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        // 创建开始时间
        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 只会触发一次
        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job2 只会触发一次
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job2", "group1")
                .build();

        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .startAt(startTime)
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job3 运行11次，运行一次，重复10次，重复间隔为10s
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job3", "group1")
                .build();

        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger3", "group3")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(10))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job4 运行6次，运行一次，重复五次，重复间隔为10s
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job4", "group1")
                .build();

        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger4", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(10)
                        .withRepeatCount(5))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job5 只会运行一次，在未来的五分钟运行
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job5", "group1")
                .build();

        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger5", "group1")
                .startAt(DateBuilder.futureDate(5, DateBuilder.IntervalUnit.MINUTE))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job6 每隔40s运行一次，无限期
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job6", "group1")
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger6", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(40)
                        .repeatForever())
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // 开始执行调度
        scheduler.start();

        // job7 在调度器开始后调用，重复20次，重复间隔5分钟
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job7", "group1")
                .build();

        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInMinutes(5)
                        .withRepeatCount(20))
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        System.out.println(job.getKey() + " will run at: " + ft + " and repeat: " + trigger.getRepeatCount() + " times, every " + trigger.getRepeatInterval() / 1000 + " seconds");

        // job8 无需trigger，直接触发
        job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job8", "group1")
                .storeDurably() // 没有 trigger 指向 job 时，保留 Job
                .build();

        scheduler.addJob(job, true);

        scheduler.triggerJob(JobKey.jobKey("job8", "group1"));

        // 等待 30秒来执行
        Thread.sleep(30L * 1000L);

        // 重新调度的job
        // job7 立刻执行，并重复10次，重复间隔1s
        trigger = (SimpleTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger7", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(1)
                        .withRepeatCount(10))
                .build();

        ft = scheduler.rescheduleJob(trigger.getKey(), trigger);
        System.out.println("job7 rescheduled to run at: " + ft);

        // 等待 5 分钟
        Thread.sleep(300L * 1000L);

        // 停止
        scheduler.shutdown(true);

        // 输出调度的任务信息
        SchedulerMetaData metaData = scheduler.getMetaData();
        System.out.println("Executed " + metaData.getNumberOfJobsExecuted() + " jobs.");
    }

}
