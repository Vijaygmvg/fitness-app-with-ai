package com.example.fitness.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.fitness.model.User;
import com.example.fitness.model.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserResponse {

	public UserResponse(User user) {
		this.id=user.getId();
		this.email=user.getEmail();
		this.firstName=user.getFirstName();
		this.lastName=user.getLastName();
		this.createdAt=user.getCreatedAt();
		this.updatedAt=user.getUpdatedAt();
		this.keycloakId=user.getKeycloakId();
	}


	private String id;
	private String keycloakId;
	
	private String email;
	

	private String firstName;
	private String lastName;
	
	
	
	

	private LocalDateTime createdAt;
	
	
	private LocalDateTime updatedAt;
	
}
