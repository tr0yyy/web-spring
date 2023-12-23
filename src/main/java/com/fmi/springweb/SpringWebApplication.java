package com.fmi.springweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = {"com.fmi.springweb.model"})
public class SpringWebApplication {

	public static void main(String[] args) {
		System.out.println("demo");
		SpringApplication.run(SpringWebApplication.class, args);
	}

}
