package com.ftn.dan.airhermes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@SpringBootApplication
public class AirhermesApplication {

	public static void main(String[] args) {
		SpringApplication.run(AirhermesApplication.class, args);
	}

	@PostConstruct
	public void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC")); // or your DB time zone
	}
}
