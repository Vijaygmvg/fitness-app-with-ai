package com.example.fitness.dto;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.example.fitness.model.UserRole;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class UserResponse {

	private String id;
	
	private String email;
	

	private String firstName;
	private String lastName;
	
	
	
	

	private LocalDateTime createdATime;
	
	
	private LocalDateTime updatedAt;
	
}
