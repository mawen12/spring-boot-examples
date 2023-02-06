package com.mawen.elasticsearch.sample.java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * Template: blog
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Asset implements Serializable {
    private static final long serialVersionUID = 4327527361871913440L;

    private Long id;

    private String chineseName;

    private List<AssetAttr> extendedAttrs;

}
