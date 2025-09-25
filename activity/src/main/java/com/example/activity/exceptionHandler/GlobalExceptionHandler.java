package com.example.activity.exceptionHandler;

import java.nio.file.attribute.UserPrincipalNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.activity.exception.UserNotFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<String> userNotFoudException(UserNotFoundException e){
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
				
	}

}
