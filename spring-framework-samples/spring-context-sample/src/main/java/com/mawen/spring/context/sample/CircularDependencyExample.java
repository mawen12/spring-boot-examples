package com.mawen.spring.context.sample;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * Spring Circular Dependency example
 * <p>
 * Bean A -> Bean B -> Bean A
 * <p>
 * - Constructor Injection will throw BeanCurrentlyInCreationException
 * - Field Injection will not throw any Exception
 * - Setter Method Injection will not throw any Exception
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/17
 */
@Configuration
public class CircularDependencyExample {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(
                CircularDependencyExample.class);

        A beanA = context.getBean(A.class);
        B beanB = context.getBean(B.class);

        context.close();
    }


    @Component
    static class A {
        // =================== Field Injection =========================
//        @Autowired
        private B b;

        // =================== Constructor Injection =========================
//        @Autowired
//        public A( B b) {
//            this.b = b;
//        }

        // =================== Setter Method Injection =========================
        @Autowired
        public void setB(B b) {
            this.b = b;
        }
    }

    @Component
    static class B {

//        @Autowired
        private A a;

//        @Autowired
//        public B(A a) {
//            this.a = a;
//        }
//
        @Autowired
        public void setA(A a) {
            this.a = a;
        }
    }



}
