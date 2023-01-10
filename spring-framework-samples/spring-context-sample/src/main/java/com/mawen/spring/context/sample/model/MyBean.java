package com.mawen.spring.context.sample.model;

import lombok.Data;

/**
 * Bean 对象
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/9
 */
@Data
public class MyBean {

    /**
     * 普通属性
     */
    private String name;

    /**
     * 其他对象
     */
    private OtherBean otherBean;

}
