package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public String DB_USERNAME = System.getenv("DB_USERNAME");

	public String DB_PASSWORD = System.getenv("DB_PASSWORD");

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
