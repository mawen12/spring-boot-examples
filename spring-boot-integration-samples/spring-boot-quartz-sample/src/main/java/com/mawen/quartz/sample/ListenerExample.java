package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.SimpleJob1;
import com.mawen.quartz.sample.listener.Job1Listener;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.matchers.KeyMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 演示 Quartz 监听器的示例，一个任务执行完再执行另一个任务
 *
 * @author mawen
 * @since 2022/12/30
 */
public class ListenerExample {

    private static final Logger log = LoggerFactory.getLogger(ListenerExample.class);

    public static void main(String[] args) throws SchedulerException, InterruptedException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        JobDetail job = JobBuilder.newJob(SimpleJob1.class)
                .withIdentity("job1")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1")
                .startNow()
                .build();

        Job1Listener listener = new Job1Listener();
        Matcher<JobKey> matcher = KeyMatcher.keyEquals(job.getKey());
        scheduler.getListenerManager().addJobListener(listener, matcher);

        scheduler.scheduleJob(job, trigger);

        scheduler.start();
        Thread.sleep(30L * 1000L);
        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
