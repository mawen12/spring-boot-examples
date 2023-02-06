package com.mawen.elasticsearch.sample.java.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;

    private String name;

    private Date timestamp;

    private Double price;
}
