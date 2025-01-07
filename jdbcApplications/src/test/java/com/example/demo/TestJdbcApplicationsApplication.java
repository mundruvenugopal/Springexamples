package com.example.demo;

import org.springframework.boot.SpringApplication;

public class TestJdbcApplicationsApplication {

	public static void main(String[] args) {
		SpringApplication.from(JdbcApplicationsApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
