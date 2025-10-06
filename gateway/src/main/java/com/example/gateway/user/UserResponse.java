package com.example.gateway.user;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserResponse {

	String id;
	private String keycloakId;
	
	private String email;
	

	private String firstName;
	private String lastName;
	
	
	
	

	private LocalDateTime createdAt;
	
	
	private LocalDateTime updatedAt;
	
}
