package com.example.talent_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "com.example.talent_api")
// @EnableJpaRepositories(basePackages="com.exmaple.talent_api")
public class TalentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentApiApplication.class, args);
	}

}
