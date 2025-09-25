package com.example.activity.service;

import org.springframework.stereotype.Service;

import com.example.activity.dto.ActivityRequest;
import com.example.activity.dto.ActivityResponse;
import com.example.activity.entity.Activity;
import com.example.activity.exception.UserNotFoundException;
import com.example.activity.repository.ActivityRepository;
import com.example.activity.utils.JsonUtils;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ActivityService {

	
	private final ActivityRepository activityRepository;
	private final UserValidationService userValidateService;
	
	public ActivityResponse trackActivity(ActivityRequest request) {
		
		if(!userValidateService.validateUser(request.getUserId()))
			throw new UserNotFoundException("invalid user id  not found"+request.getUserId());
		Activity activity=Activity.builder().userId(request.getUserId())
				.type(request.getType())
				.caloriesBurn(request.getCaloriesBurn())
				.duration(request.getDuration())
				.startTime(request.getStartTime())
				.additionalMetrics(JsonUtils.mapToJson(request.getAdditionalMetrics()))
				.build();
		Activity savedActivity=activityRepository.save(activity);
		return mapToActivityResponse(savedActivity);
	}
	public ActivityResponse mapToActivityResponse(Activity savedActivity) {
		return ActivityResponse.builder().
				id(savedActivity.getId())
				.userId(savedActivity.getUserId())
				.type(savedActivity.getType())
				.caloriesBurn(savedActivity.getCaloriesBurn())
				.duration(savedActivity.getDuration())
				.startTime(savedActivity.getStartTime())
				.additionalMetrics(JsonUtils.jsonToMap(savedActivity.getAdditionalMetrics()))
				.createdAt(savedActivity.getCreatedAt())
				.updatedAt(savedActivity.getUpdatedAt())
				.build();
	}

}
