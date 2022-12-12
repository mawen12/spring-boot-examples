package com.mawen;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;


//@SpringBootApplication(scanBasePackages = {"com.mawen.config"})
//@Configuration
@EnableAutoConfiguration
//@ComponentScan
public class FirstAppByGuiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FirstAppByGuiApplication.class, args);
	}

	/**
	 * {@link ApplicationRunner#run(ApplicationArguments)} 方法在
	 * Spring Boot 应用启动后回调
	 *
	 * @param context WebServerApplicationContext
	 * @return ApplicationRunner Bean
	 */
//	@Bean
//	public ApplicationRunner runner(WebServerApplicationContext context) {
//		return args -> System.out.println("当前 WebServer 实现类为：" + context.getWebServer().getClass().getName());
//	}

}
