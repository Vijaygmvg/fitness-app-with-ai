package com.example.aiadvice.model;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@Entity

@Table(name = "recommendations")
public class Recomendation {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String activityId;
    private String userId;
    
    @Lob
    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;
    private String type;

    @Lob
    @Column(name = "improvements", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> improvements;

    @Lob
    @Column(name = "suggetions", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> suggestions;

    @Lob
    @Column(name = "safety", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> safety;

    @CreationTimestamp
    private LocalDateTime createdAt;

	@Override
	public String toString() {
		return "Recomendation [id=" + id + ", activityId=" + activityId + ", userId=" + userId + ", recommendation="
				+ recommendation + ", type=" + type + ", improvements=" + improvements + ", suggestions=" + suggestions
				+ ", safety=" + safety + ", createdAt=" + createdAt + "]";
	}
	
	public Recomendation() {
		
	}
    
    
}
