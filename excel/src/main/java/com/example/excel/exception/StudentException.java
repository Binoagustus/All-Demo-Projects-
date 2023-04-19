package com.example.excel.exception;

public class StudentException extends RuntimeException{

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public StudentException(String message) {
		this.message = message;
	}
}
