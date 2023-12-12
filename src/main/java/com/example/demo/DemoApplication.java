package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties
public class DemoApplication {

	public String PORT = System.getenv("PORT");

	public String DB_URL = System.getenv("DB_URL");

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
