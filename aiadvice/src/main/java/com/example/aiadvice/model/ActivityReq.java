package com.example.aiadvice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActivityReq {


	
	private String id;
	
	private String userId;
	
	
	private ActivityType type;
	
	private Integer duration;
	
	private Integer caloriesBurn;
	
	private LocalDateTime startTime;
	 @Lob
	private String additionalMetrics;
	

	private LocalDateTime createdAt;
	
	
	private LocalDateTime updatedAt;


	@Override
	public String toString() {
		return "ActivityReq [id=" + id + ", userId=" + userId + ", type=" + type + ", duration=" + duration
				+ ", caloriesBurn=" + caloriesBurn + ", startTime=" + startTime + ", additionalMetrics="
				+ additionalMetrics + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}

}
