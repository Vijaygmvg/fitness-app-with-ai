package com.example.fitness.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity 
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	@Column(unique=true)
	private String email;
	@Column(nullable = false)
	private String password;
	private String firstName;
	private String lastName;
	
	@Enumerated(EnumType.STRING)
	private UserRole uerRole=UserRole.USER;
	
	@CreationTimestamp
	private LocalDateTime createdATime;
	
	@UpdateTimestamp
	private LocalDateTime updatedAt;
	
}
