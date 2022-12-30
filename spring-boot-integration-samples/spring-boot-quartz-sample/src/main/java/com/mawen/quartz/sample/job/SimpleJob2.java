package com.mawen.quartz.sample.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * @author mawen
 * @since 2022/12/30
 */
public class SimpleJob2 implements Job {

    private static final Logger log = LoggerFactory.getLogger(SimpleJob2.class);

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobKey jobKey = context.getJobDetail().getKey();
        log.info("SimpleJob2 says: {} executing at {}.", jobKey, new Date());
    }
}
