package com.mawen.spring.context.sample;

import java.lang.reflect.Field;

/**
 * Idea Field Break Point example
 * <p>
 * - 普通方法调用可以监测
 * - 反射方法调用无法监测
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/17
 */
public class FieldBreakPointExample {

    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        A a = new A();
        System.out.println(a.ready);

        // common method invoke
        a.setReady(true);

        System.out.println(a.ready);

        // reflect method invoke
        Field readyField = a.getClass().getDeclaredField("ready");
        readyField.setAccessible(true);
        readyField.set(a, false);

        System.out.println(a.ready);
    }


    static class A {
        private boolean ready;

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }
    }
}
