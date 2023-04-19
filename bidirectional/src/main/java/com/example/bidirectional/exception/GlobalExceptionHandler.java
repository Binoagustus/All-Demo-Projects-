package com.example.bidirectional.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.bidirectional.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = BiDirectionalException.class)
	public Response handleBiDirectionalException(BiDirectionalException exception) {
		return new Response(exception.getMessage());
	}
}
