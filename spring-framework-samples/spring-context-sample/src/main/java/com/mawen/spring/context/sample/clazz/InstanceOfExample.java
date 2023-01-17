package com.mawen.spring.context.sample.clazz;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * instanceOf 示例
 * <p>
 * a instanceOf B
 * <p>
 * B.class.isAssignableFrom(a.getClass())
 * <p>
 * instanceOf 作用于compile-time，两侧只能用于对象类型，不能用于原始类型
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/16
 */
public class InstanceOfExample {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        System.out.println("list instanceOf List " + (list instanceof List));
        System.out.println("list instanceOf List " + (list instanceof AbstractList));
        System.out.println("list instanceOf List " + (list instanceof ArrayList));
    }

}
