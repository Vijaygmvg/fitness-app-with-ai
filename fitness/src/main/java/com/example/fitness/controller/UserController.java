package com.example.fitness.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fitness.dto.RegisterRequest;
import com.example.fitness.dto.UserResponse;
import com.example.fitness.repository.UserRepository;
import com.example.fitness.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
	
	private final  UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest registerRequest){
		return ResponseEntity.ok(userService.addUser(registerRequest));
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponse> getUser(@PathVariable("userId") String id){
		 UserResponse userResponse =userService.findUser(id);
		 if(userResponse!=null)
			  return ResponseEntity.ok(userResponse);
		 return new ResponseEntity<UserResponse>(userResponse,HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/{userId}/validate")
	public ResponseEntity<Boolean> validateUser(@PathVariable("userId") String id){
		return ResponseEntity.ok(userService.existsByuserId(id));
		
	}
	
	
	

}
