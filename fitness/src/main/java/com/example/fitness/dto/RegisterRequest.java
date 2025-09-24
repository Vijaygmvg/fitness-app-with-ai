package com.example.fitness.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {
	
	@NotBlank(message="email is required")
	@Email(message="enter a valid email")
    private String email;
	
	@NotBlank(message="passwordis required")
	@Size(min=6,message="minimum 6 char are required ")
	private String password;
	

	private String firstName;
	private String lastName;

}
