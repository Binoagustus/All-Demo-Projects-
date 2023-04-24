package com.example.demo.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.demo.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = DemoException.class)
	public Response handleDemoException(DemoException exception) {
		return new Response(exception.getMessage());
	}
}
