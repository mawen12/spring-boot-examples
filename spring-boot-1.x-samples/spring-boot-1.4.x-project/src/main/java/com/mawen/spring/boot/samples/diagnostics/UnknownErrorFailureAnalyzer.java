package com.mawen.spring.boot.samples.diagnostics;

import org.springframework.boot.diagnostics.FailureAnalysis;
import org.springframework.boot.diagnostics.FailureAnalyzer;

/**
 * {@link UnknownError} 自定义 {@link FailureAnalyzer} 实现，分析并生成 {@link FailureAnalysis}
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/23
 */
public class UnknownErrorFailureAnalyzer implements FailureAnalyzer{

    @Override
    public FailureAnalysis analyze(Throwable throwable) {
        if (throwable instanceof UnknownError) {
            return new FailureAnalysis("未知错误", "请重启尝试", throwable);
        }
        return null;
    }
}
