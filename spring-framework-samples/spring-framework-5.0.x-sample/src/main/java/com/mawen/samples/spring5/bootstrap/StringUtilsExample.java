package com.mawen.samples.spring5.bootstrap;

import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/10
 */
public class StringUtilsExample {

    public static void main(String[] args) {
        String str = "default,dev;prod poc";
        String[] strings = StringUtils.tokenizeToStringArray(str, ",; ");
        System.out.println(Arrays.toString(strings));

    }

}
