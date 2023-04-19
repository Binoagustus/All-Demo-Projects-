package com.example.excel.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.excel.Response;


@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(value = StudentException.class)
	public Response studentException(StudentException exception) {
		return new Response(exception.getMessage());
	}
}
