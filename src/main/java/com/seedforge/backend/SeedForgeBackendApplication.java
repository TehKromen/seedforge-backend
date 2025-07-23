package com.seedforge.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.seedforge.backend.infrastructure.adapters.output.persistence.entity")
public class SeedForgeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(SeedForgeBackendApplication.class, args);
	}

}
