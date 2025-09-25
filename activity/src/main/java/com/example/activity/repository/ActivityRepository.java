package com.example.activity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.activity.entity.Activity;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, String> {

}
