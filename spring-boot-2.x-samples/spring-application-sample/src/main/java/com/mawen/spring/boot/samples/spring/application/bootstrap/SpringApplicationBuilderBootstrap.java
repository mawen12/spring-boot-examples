package com.mawen.spring.boot.samples.spring.application.bootstrap;

import org.springframework.boot.Banner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * Fluent Builder to override default value
 *
 * @author <a href="1181963012mw@gmail.com">mawen12</a>
 * @since 2023/2/18
 */
@EnableAutoConfiguration
public class SpringApplicationBuilderBootstrap {

    public static void main(String[] args) {

        new SpringApplicationBuilder(SpringApplicationBootstrap.class)
                .web(WebApplicationType.NONE)
                .bannerMode(Banner.Mode.OFF)
                .profiles("prod")
                .headless(true)
                .run(args);

    }
}
