package com.mawen.spring.context.sample.registerbean.service;

/**
 * 日志服务
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/11
 */
public interface LogService {

    void log(String msg);

    public static class LogServiceImpl implements LogService {

        public LogServiceImpl() {
            System.out.printf("instance of %s created: %s%n",
                              this.getClass().getName(),
                              System.identityHashCode(this));
        }

        private void init() {
            System.out.printf("%s, init methdo called: ",
                              this.getClass().getName(),
                              System.identityHashCode(this));
        }

        @Override
        public void log(String msg) {
            System.out.println(msg);
        }
    }

}
