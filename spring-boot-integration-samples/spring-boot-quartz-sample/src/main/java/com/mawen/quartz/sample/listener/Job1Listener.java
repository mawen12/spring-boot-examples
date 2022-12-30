package com.mawen.quartz.sample.listener;

import com.mawen.quartz.sample.job.SimpleJob2;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mawen
 * @since 2022/12/30
 */
public class Job1Listener implements JobListener {

    private static final Logger log = LoggerFactory.getLogger(Job1Listener.class);

    @Override
    public String getName() {
        return "job1_to_job2";
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        log.info("Job1Listener says: Job is about to be executed.");
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {
        log.info("Job1Listener says: Job Execution was vetoed.");
    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        log.info("Job1Listener says: Job was executed.");

        JobDetail job2 = JobBuilder.newJob(SimpleJob2.class)
                .withIdentity("job2")
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("job2Trigger")
                .startNow()
                .build();

        try {
            context.getScheduler().scheduleJob(job2, trigger);
        } catch (SchedulerException e) {
            log.warn("Unable to schedule job2!");
            e.printStackTrace();
        }
    }
}
