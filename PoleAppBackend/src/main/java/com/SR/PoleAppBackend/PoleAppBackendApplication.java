package com.SR.PoleAppBackend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication

@EnableScheduling 

public class PoleAppBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoleAppBackendApplication.class, args);
	}

}
