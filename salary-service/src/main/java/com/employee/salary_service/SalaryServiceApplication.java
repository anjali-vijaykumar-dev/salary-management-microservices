package com.employee.salary_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class SalaryServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalaryServiceApplication.class, args);
	}

}
