package com.mawen.logging.sample;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Log Level Hierarchy
 * - TRACE 最细粒度
 * - DEBUG
 * - INFO
 * - WARN
 * - ERROR
 * - FATAL
 *
 * @author mawen
 * @since 2023/1/3
 */
public class LoggingExample {

    static final Logger log = LogManager.getLogger();

    public static void main(String[] args) {
        log.trace("log message");
        log.debug("log message");
        log.info("log message");
        log.warn("log message");
        log.error("log message");
        log.fatal("log message");
    }

}
