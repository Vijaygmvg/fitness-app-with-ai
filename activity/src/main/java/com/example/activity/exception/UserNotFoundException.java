package com.example.activity.exception;

public class UserNotFoundException extends RuntimeException{
	
	public UserNotFoundException(String message) {
		super(message);
	}

}
