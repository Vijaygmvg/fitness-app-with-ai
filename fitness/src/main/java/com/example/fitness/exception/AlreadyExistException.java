package com.example.fitness.exception;

import org.apache.logging.log4j.message.Message;

public class AlreadyExistException extends RuntimeException {
	
	

	public AlreadyExistException(String message) {
		super(message);
	}

}
