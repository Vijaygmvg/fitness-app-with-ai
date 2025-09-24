package com.example.fitness.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.fitness.model.User;

public interface  UserRepository extends JpaRepository<User,String>{

}
