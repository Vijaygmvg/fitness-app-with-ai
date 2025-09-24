package com.example.fitness.service;

import org.springframework.stereotype.Service;

import com.example.fitness.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	
	private final UserRepository userRepository;
}
