package com.example.bcsd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BcsdApplication {

	public static void main(String[] args) {
		SpringApplication.run(BcsdApplication.class, args);
	}

}
