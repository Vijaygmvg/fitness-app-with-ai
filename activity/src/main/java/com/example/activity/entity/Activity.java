package com.example.activity.entity;

import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="activities")
public class Activity {

	
	private String id;
	
	private String userId;
	
	private ActivityType type;
	
	private Integer duration;
	
	private Integer caloriesBurn;
	
	private LocalDateTime startTime;
	
	private Map<String,Object> additionalMetrics;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
