package com.example.fitness.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.fitness.dto.RegisterRequest;
import com.example.fitness.dto.UserResponse;
import com.example.fitness.exception.AlreadyExistException;
import com.example.fitness.model.User;
import com.example.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	
	private final UserRepository userRepository;
	
	
	public UserResponse addUser(RegisterRequest registerRequest) {
		  if(userRepository.existsByEmail(registerRequest.getEmail())) {
			  UserResponse userReponse=new UserResponse(userRepository.findByEmail(registerRequest.getEmail()));
			  return userReponse;
			   
		  }
		  User user=new User();
		  user.setEmail(registerRequest.getEmail());
		  user.setPassword(registerRequest.getPassword());
		  user.setFirstName(registerRequest.getFirstName());
		  user.setKeycloakId(registerRequest.getKeycloakId());
		  user.setLastName(registerRequest.getLastName());
		  User savedUser=userRepository.save(user);
		  
		  UserResponse userReponse=new UserResponse(savedUser);
		  return userReponse;
		
	}


	public  UserResponse findUser(String id) {
		Optional<User> user=userRepository.findById(id);
		if(!user.isPresent())
			return null;
		
		return new UserResponse(user.get());
	}


	public Boolean existsByuserId(String id) {
		return userRepository.existsByKeycloakId(id);
	}
}
