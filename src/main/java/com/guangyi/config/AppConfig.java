package com.guangyi.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Created by zhongjing on 5/11/16.
 */
@Configuration
@ComponentScan({"com.guangyi"})
@EnableWebMvc
@EnableScheduling
@EnableTransactionManagement
public class AppConfig {

	@Bean
	public static PropertySourcesPlaceholderConfigurer propertyPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}

	@Configuration
	@Profile("development")
	@PropertySource("classpath:application.development.properties")
	static class Development {}

}
