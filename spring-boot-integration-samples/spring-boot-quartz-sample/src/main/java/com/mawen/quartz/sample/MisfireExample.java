package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.StatefulDumbJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 演示 StatefulJob 的行为，以及 misfire 指令如何影响 StatefulJob 的触发器 - 当作业的执行频率超过触发器的频率时
 * 当示例运行时，需要注意到有两个相同调度的触发器触发任务时，触发器每3秒调用一次，但是任务需要10s去执行。因此当任务执行完成时，触发器已经 misfire 了。（如果调度器的 misfire threshold 设置超过7s，那么将不会发生 misfire）.
 *
 *
 * @author mawen
 * @since 2022/12/30
 */
public class MisfireExample {

    private final static Logger log = LoggerFactory.getLogger(MisfireExample.class);

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        Date startTime = DateBuilder.nextGivenSecondDate(null, 15);

        // job1 每3秒运行一次
        JobDetail job = JobBuilder.newJob(StatefulDumbJob.class)
                .withIdentity("job1", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(startTime)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever())
                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        // job2 每3秒运行一次，但是会延迟10s，并且在几次执行后出现 misfire
        job = JobBuilder.newJob(StatefulDumbJob.class)
                .withIdentity("job2", "group1")
                .usingJobData(StatefulDumbJob.EXECUTION_DELAY, 10000L)
                .build();

        trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger2", "group1")
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInSeconds(3)
                        .repeatForever()
                        .withMisfireHandlingInstructionNowWithExistingCount())
                .build();

        ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        scheduler.start();
        Thread.sleep(600L * 1000L);
        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
