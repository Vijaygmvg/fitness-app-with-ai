package com.example.aiadvice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.aiadvice.exception.RecomendationNotFoundException;
import com.example.aiadvice.model.Recomendation;
import com.example.aiadvice.repository.RecomendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecomendationService {
	
	private final RecomendationRepository recomendationRepository;

	public List<Recomendation> getUserRecomendation(String userId) {
		return recomendationRepository.findByUserId(userId);
	}

	public Recomendation getActivityRecomendation(String activityId) {
		return recomendationRepository.findByActivityId(activityId).orElseThrow(()->new RecomendationNotFoundException("No recomendation found for this activity id"+activityId));
	}

}
