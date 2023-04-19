package com.example.jwtapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/blog")
public class SampleController {

	@GetMapping("/message/{a}/{b}")
	public int getMessage(@PathVariable int a, @PathVariable int b) {
		System.out.println("controller method");
//		return new ResponseEntity<>("Hai the sum is .." + (a + b), HttpStatus.OK);
		return (a + b);
	}

	@GetMapping("/restricted")
	public ResponseEntity<?> getRestrictedMessage() {
		return new ResponseEntity<>("This is a restricted message", HttpStatus.OK);
	}
	
}
