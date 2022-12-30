package com.mawen.quartz.sample;

import com.mawen.quartz.sample.job.SimpleJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.AnnualCalendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Quartz 通过日历设置调度
 *
 * @author mawen
 * @since 2022/12/30
 */
public class CalendarExample {

    private static final Logger log = LoggerFactory.getLogger(CalendarExample.class);

    public static void main(String[] args) throws SchedulerException {
        Scheduler scheduler = new StdSchedulerFactory()
                .getScheduler();

        AnnualCalendar holidays = new AnnualCalendar();

        GregorianCalendar fourthOfJuly = new GregorianCalendar(2005, 6, 4);
        holidays.setDayExcluded(fourthOfJuly, true);

        GregorianCalendar halloween = new GregorianCalendar(2005, 9, 31);
        holidays.setDayExcluded(halloween, true);

        GregorianCalendar christmas = new GregorianCalendar(2005, 11, 25);
        holidays.setDayExcluded(christmas, true);

        scheduler.addCalendar("holidays", holidays, false, false);

        Date runDate = DateBuilder.dateOf(0, 0, 10, 31, 10);

        JobDetail job = JobBuilder.newJob(SimpleJob.class)
                .withIdentity("job1", "group1")
                .build();

        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .startAt(runDate)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()
                        .withIntervalInHours(1)
                        .repeatForever())
                .modifiedByCalendar("holidays")
                .build();

        Date ft = scheduler.scheduleJob(job, trigger);
        log.info("{} will run at {} and repeat: {} times, every {} seconds", job.getKey(), ft, trigger.getRepeatCount(), trigger.getRepeatInterval() / 1000);

        scheduler.start();
        try {
            Thread.sleep(30L * 1000L);
        } catch (InterruptedException e) {

        }

        scheduler.shutdown(true);

        SchedulerMetaData metaData = scheduler.getMetaData();
        log.info("Executed {} jobs.", metaData.getNumberOfJobsExecuted());
    }

}
