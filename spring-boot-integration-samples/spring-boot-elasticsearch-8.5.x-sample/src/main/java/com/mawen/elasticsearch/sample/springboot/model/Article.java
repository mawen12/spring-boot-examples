package com.mawen.elasticsearch.sample.springboot.model;

import lombok.Data;

import java.util.Date;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/28
 */
@Data
public class Article {

    private String id;

    private String title;

    private Date timestamp;
}
