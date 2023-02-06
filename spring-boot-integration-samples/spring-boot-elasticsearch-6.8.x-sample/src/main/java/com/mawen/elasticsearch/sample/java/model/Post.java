package com.mawen.elasticsearch.sample.java.model;

import com.oracle.webservices.internal.api.databinding.DatabindingMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {

    private Long id;

    private String user;

    private Date postDate;

    private String message;

}
