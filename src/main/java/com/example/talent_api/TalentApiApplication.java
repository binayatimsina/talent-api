package com.example.talent_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages = "com.example.talent_api.repository")
public class TalentApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(TalentApiApplication.class, args);
	}

}
