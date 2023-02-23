package com.mawen.spring.boot.samples.diagnostics;

import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalysisReporter;

/**
 * 自定义 {@link FailureAnalysisReporter}，控制台输出 {@link FailureAnalysis}
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/23
 */
public class ConsoleFailureAnalysisReporter implements FailureAnalysisReporter {

    @Override
    public void report(FailureAnalysis failureAnalysis) {
        System.out.printf("故障描述: %s \n执行动作: %s \n异常堆栈: %s \n",
                failureAnalysis.getDescription(),
                failureAnalysis.getAction(),
                failureAnalysis.getCause());
    }
}
