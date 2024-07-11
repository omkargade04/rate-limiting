package com.ratelimiter.rateLimiter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.ratelimiter.rateLimiter")
public class RateLimiterApplication {

	public static void main(String[] args) {

		SpringApplication.run(RateLimiterApplication.class, args);
	}

}
