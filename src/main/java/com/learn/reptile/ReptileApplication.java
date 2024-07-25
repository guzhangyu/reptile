package com.learn.reptile;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.learn.reptile")
public class ReptileApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ReptileApplication.class, args);
	}

}
