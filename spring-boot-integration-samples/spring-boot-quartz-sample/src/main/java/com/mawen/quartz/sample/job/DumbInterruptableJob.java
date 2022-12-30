package com.mawen.quartz.sample.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 示例：支持打断的任务
 *
 * @author mawen
 * @since 2022/12/30
 */
public class DumbInterruptableJob implements InterruptableJob {

    private final static Logger log = LoggerFactory.getLogger(DumbInterruptableJob.class);

    private boolean interrupted = false;

    private JobKey jobKey;

    @Override
    public void interrupt() throws UnableToInterruptJobException {
        log.info("---{} -- INTERRUPTING", jobKey);
        interrupted = true;
    }

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        jobKey = context.getJobDetail().getKey();
        log.info("--- {} executing at {}", jobKey, new Date());

        try {
            for (int i = 0; i < 4; i++) {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 定期检查是否被打断
                if (interrupted) {
                    log.info("--- {} -- Interrupted... bailing out!", jobKey);
                    return;
                }
            }
        } finally {
            log.info("--- {} completed at {}", jobKey, new Date());
        }
    }
}
