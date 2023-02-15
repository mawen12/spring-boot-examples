package com.mawen.spring.boot.samples.autoconfigure.formatter;

/**
 * 默认格式化实现
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/15
 * @see Formatter
 */
public class DefaultFormatter implements Formatter {

    @Override
    public String format(Object object) {
        return String.valueOf(object); // null 安全实现
    }
}
