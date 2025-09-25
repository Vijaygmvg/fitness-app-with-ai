package com.example.activity.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.example.activity.entity.Activity;
import com.example.activity.entity.ActivityType;
import com.example.activity.utils.JsonUtils;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActivityResponse {



private String id;
	
	private String userId;
	
	
	private ActivityType type;
	
	private Integer duration;
	
	private Integer caloriesBurn;
	
	private LocalDateTime startTime;
	 
	private Map<String,Object> additionalMetrics;
	
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
}
