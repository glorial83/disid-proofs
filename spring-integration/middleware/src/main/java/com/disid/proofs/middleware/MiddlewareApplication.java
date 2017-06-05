package com.disid.proofs.middleware;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
// Loading all the integration xml files
@ImportResource("classpath:integration/*.xml")
public class MiddlewareApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiddlewareApplication.class, args);
	}
}
