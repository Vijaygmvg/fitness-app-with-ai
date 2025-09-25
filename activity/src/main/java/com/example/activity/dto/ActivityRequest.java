package com.example.activity.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.example.activity.entity.ActivityType;

import lombok.Data;

  @Data
public class ActivityRequest {
	
private String userId;
	
	
	private ActivityType type;
	
	private Integer duration;
	
	private Integer caloriesBurn;
	
	private LocalDateTime startTime;

	private Map<String,Object> additionalMetrics;
	
}
