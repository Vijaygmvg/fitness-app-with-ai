package com.example.activity.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="activities")
@Builder

public class Activity {

	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	
	private String userId;
	
	@Enumerated(EnumType.STRING)
	private ActivityType type;
	
	private Integer duration;
	
	private Integer caloriesBurn;
	
	private LocalDateTime startTime;
	 @Lob
	private String additionalMetrics;
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
}
