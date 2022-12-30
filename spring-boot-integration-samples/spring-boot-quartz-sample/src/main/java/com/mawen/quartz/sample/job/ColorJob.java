package com.mawen.quartz.sample.job;

import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 用于接受参数和保持状态的简单任务
 *
 * @author mawen
 * @since 2022/12/30
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ColorJob implements Job {

    private static Logger log = LoggerFactory.getLogger(ColorJob.class);

    public static final String FAVORITE_COLOR = "favorite color";

    public static final String EXECUTION_COUNT = "count";

    /**
     * Quartz 每次执行时，都会重新初始化该类，非静态成员变量不能用于保持状态
     */
    private int counter = 1;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        // 运行时打印任务名称和时间
        JobKey jobKey = context.getJobDetail().getKey();

        // 抓取并打印传递的参数
        JobDataMap data = context.getJobDetail().getJobDataMap();
        String favoriteColor = data.getString(FAVORITE_COLOR);
        int count = data.getInt(EXECUTION_COUNT);
        log.info("ColorJob: {} executing at {}\n favorite color is {}\n execution count (from job map) is {}\n execution count (from job member variable) is {}", jobKey, new Date(), favoriteColor, count, counter);
        // 自增count，并将值存储到map中，确保状态被保持
        count++;
        data.put(EXECUTION_COUNT, count);

        // 自增局部变量，没有任何实际意义，因为任务状态无法被保存到成员变量中
        counter++;
    }
}
