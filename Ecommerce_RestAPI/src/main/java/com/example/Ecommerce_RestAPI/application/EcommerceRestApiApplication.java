package com.example.Ecommerce_RestAPI.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.example"})
public class EcommerceRestApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcommerceRestApiApplication.class, args);
	}

}
