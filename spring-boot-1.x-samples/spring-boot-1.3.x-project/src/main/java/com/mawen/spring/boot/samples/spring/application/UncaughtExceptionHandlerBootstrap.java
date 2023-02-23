package com.mawen.spring.boot.samples.spring.application;

/**
 * {@link Thread.UncaughtExceptionHandler} 引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/23
 */
public class UncaughtExceptionHandlerBootstrap {

    public static void main(String[] args) {
        Thread mianThread = Thread.currentThread();
        System.out.printf("当前执行线程 %s!\n", mianThread.getName());
        // 为 main 线程设置 UncaughtExceptionHandler 实现
        mianThread.setUncaughtExceptionHandler((thread, throwable) -> {
            System.out.printf("处理线程[%s]的非捕获异常，详情: %s\n",
                    thread.getName(), throwable.getMessage());
            System.exit(0);
        });

        throw new RuntimeException("故意抛出异常，测试 UncaughtExceptionHandler 是否处理!");
    }
}
