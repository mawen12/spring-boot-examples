package com.mawen.sample.aop.jdk;

import com.mawen.sample.aop.DefaultEchoService;
import com.mawen.sample.aop.EchoService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * JDK 动态代理示例
 * <p>
 * 凭空去创建一个代理类，在运行时生成字节码
 * <p>
 * JDK 仅支持接口生成代理类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/1/6
 */
public class JdkDynamicProxyDemo {

    public static void main(String[] args) {
        DefaultEchoService defaultEchoService = new DefaultEchoService();

        InvocationHandler handler = (proxy, method, args1) -> {
            if (EchoService.class.isAssignableFrom(proxy.getClass())) {
                return method.invoke(defaultEchoService, args1);
            }
            return null;
        };
        Object proxy = Proxy.newProxyInstance(EchoService.class.getClassLoader(),
                                              new Class[]{EchoService.class},
                                              handler);

        // $Proxy0 定义
        // class $Proxy0 extends Proxy implements EchoService {
        //      $Proxy0 (InvocationHandler handler) {
        //          super(handler);
        //      }
        // }
        System.out.println(Proxy.isProxyClass(proxy.getClass()));
        System.out.println(proxy.getClass());
        System.out.println(proxy.getClass().getSuperclass());
        System.out.println(Arrays.toString(proxy.getClass().getInterfaces()));

        EchoService echoService = (EchoService) proxy;
        System.out.println(echoService.echo("Hello World"));
    }

}
