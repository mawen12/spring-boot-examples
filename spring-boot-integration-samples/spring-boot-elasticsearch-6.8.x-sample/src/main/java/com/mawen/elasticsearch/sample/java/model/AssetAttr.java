package com.mawen.elasticsearch.sample.java.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/6
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssetAttr implements Serializable {
    private static final long serialVersionUID = 4028544428802937755L;

    private String keyLabel;

    private String keyCode;

    private String valueLabel;

    private String valueId;
}
