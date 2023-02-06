package com.mawen.elasticsearch.sample.java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Human {

    private Long id;

    private String name;

    private List<Cat> cats;

}
