package com.example.aiadvice.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.aiadvice.model.Recomendation;

@Repository
public interface RecomendationRepository extends JpaRepository<Recomendation, String> {

	List<Recomendation> findByUserId(String userId);

	Optional<Recomendation> findByActivityId(String activityId);

}
