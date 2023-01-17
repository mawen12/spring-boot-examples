package com.mawen.spring.context.sample.clazz;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * {@link Class}示例
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/16
 */
public class ClassExample {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();

        System.out.println("Collection isAssignableFrom " + Collection.class.isAssignableFrom(list.getClass()));
        System.out.println("List isAssignableFrom " + List.class.isAssignableFrom(list.getClass()));
        System.out.println("AbstractList isAssignableFrom " + AbstractList.class.isAssignableFrom(list.getClass()));
        System.out.println("ArrayList isAssignableFrom " + ArrayList.class.isAssignableFrom(list.getClass()));

        System.out.println("Collection isInstance " + Collection.class.isInstance(list));
        System.out.println("List isInstance " + List.class.isInstance(list));
        System.out.println("AbstractList isInstance " + AbstractList.class.isInstance(list));
        System.out.println("ArrayList isInstance " + ArrayList.class.isInstance(list));
    }

}
