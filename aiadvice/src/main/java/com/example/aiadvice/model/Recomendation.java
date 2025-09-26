package com.example.aiadvice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
@Table(name="recomendations")

public class Recomendation {

	
	@Id
	@GeneratedValue(strategy=GenerationType.UUID)
	private String id;
	private String activityId;
	private String userId;
	private String recomendation;
	private List<String> improvements;
	private List<String> suggestions;
	private List<String> safety;
	
	@CreationTimestamp
	private LocalDateTime createdAt;
}
