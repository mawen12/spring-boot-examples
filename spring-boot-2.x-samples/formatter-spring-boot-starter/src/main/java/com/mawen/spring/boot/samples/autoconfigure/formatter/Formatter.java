package com.mawen.spring.boot.samples.autoconfigure.formatter;

/**
 * 对象格式化接口
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/15
 */
public interface Formatter {

    /**
     * 格式化操作
     *
     * @param object 待格式化对象
     * @return 返回格式化后的内容
     */
    String format(Object object);

}
