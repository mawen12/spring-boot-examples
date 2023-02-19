package com.mawen.spring.boot.samples.auto.configuration.bootstrap;

import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * URL 资源引导类
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/18
 */
public class URLBootstrap {

    public static void main(String[] args) throws IOException {
        // 构建 URL 对象
        URL url = new URL("https://github.com/spring-projects/spring-boot");
        // 获取 URLConnection 对象
        URLConnection connection = url.openConnection();
        // 自动关闭 InputStream
        try (InputStream inputStream = connection.getInputStream()) {
            // 复制资源到标准输出流
            StreamUtils.copy(inputStream, System.out);
        }
    }

}
