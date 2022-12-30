package com.mawen.quartz.sample.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Set;

/**
 * @author mawen
 * @since 2022/12/30
 */
public class SimpleJob3 implements Job {

    private static final Logger log = LoggerFactory.getLogger(SimpleJob3.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("Executing job: {} executing at {}, fired by: {}", jobKey, new Date(), context.getTrigger().getKey());

        if (context.getMergedJobDataMap().size() > 0) {
            Set<String> keys = context.getMergedJobDataMap().keySet();
            for (String key : keys) {
                String val = context.getMergedJobDataMap().getString(key);
                log.info(" - jobDataMap entry: {} = {}", key, val);
            }
        }
    }
}
