package com.example.bidirectional.exception;

public class BiDirectionalException extends RuntimeException{

	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public BiDirectionalException(String message) {
		this.message = message;
	}
}
