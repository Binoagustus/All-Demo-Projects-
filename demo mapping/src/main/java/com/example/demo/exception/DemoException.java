package com.example.demo.exception;

public class DemoException extends RuntimeException{

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public DemoException(String message) {
		this.message = message;
	}
}
