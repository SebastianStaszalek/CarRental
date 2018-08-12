package com.capgemini.jstk.carrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CarRentalApplication {

	public static void main(String[] args) {
		System.setProperty("spring.profiles.active", "mysql");

		SpringApplication.run(CarRentalApplication.class, args);
	}
}
