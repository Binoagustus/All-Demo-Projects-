package com.example.jwtapp;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import jakarta.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class JwtAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(JwtAppApplication.class, args);
	}

	@PostConstruct
	public void printTime() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);
		System.out.println(strDate);
	}
}
