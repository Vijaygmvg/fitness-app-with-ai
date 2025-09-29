package com.example.aiadvice.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.aiadvice.model.ActivityReq;
import com.example.aiadvice.model.Recomendation;
import com.example.aiadvice.model.StringListConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.Convert;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class ActivityAiService {

	private final GeminiService geminiService;

	public Recomendation generateRecomendation(ActivityReq activity) {
		String prompt = createPrompt(activity);
		log.info("the recomendation  for the activity is ");
		// geminiService.getRecomendations(prompt)

		return processAIResponse(activity, geminiService.getRecomendations(prompt));
	}

	private Recomendation processAIResponse(ActivityReq activity,String aiResponse) {
	
	try {
		ObjectMapper mapper=new ObjectMapper();
	  JsonNode rootNode=mapper.readTree(aiResponse);
       String text = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
       text = text
    		    .replaceAll("(?i)```json", "")
    		    .replaceAll("(?i)```", "")
    		    .replaceAll("(?i)'''json", "")
    		    .replaceAll("(?i)'''", "")
    		    .trim();

		for(int i=0;i<5;i++)
		 System.out.println(i);
	
		System.out.println(text);
		JsonNode analysisJson =mapper.readTree(text);
		JsonNode analysisNode=analysisJson.path("analysis");
		StringBuilder fullAnalysis=new StringBuilder();
		addAnalysisSection(fullAnalysis,analysisNode,"overall","overall:");
		addAnalysisSection(fullAnalysis,analysisNode,"pace","pace:");
		addAnalysisSection(fullAnalysis,analysisNode,"heartRate","heartRate:");
		addAnalysisSection(fullAnalysis,analysisNode,"caloriesBurned","caloriesBurned:");
		List<String> improvements=extractImprovements(analysisJson.path("improvements"));
        List<String> suggetions=extractSuggetions(analysisJson.path("suggestions"));
        List<String> safety=extractsafety(analysisJson.path("safety"));
        return Recomendation.builder()
        		.activityId(activity.getId())
        		.userId(activity.getUserId())
        		.type(activity.getType().toString())
        		.improvements(improvements)
        		.suggestions(suggetions)
        		.recommendation(fullAnalysis.toString().trim())
        		.safety(safety)
        	   .createdAt(LocalDateTime.now())
        		.build();
        		
        		


    
    
 	}
	
		catch(Exception e) {
		e.printStackTrace();
		  return Recomendation.builder()
        		.activityId(activity.getId())
        		.userId(activity.getUserId())
        		.type(activity.getType().toString())
        		.improvements(Collections.singletonList("continue with same routine"))
        		.suggestions(Collections.singletonList("no suggetion for you"))
        		.recommendation("good heath")
        		.safety(Collections.singletonList("good health eep rnning keep doing activity "))
        	   .createdAt(LocalDateTime.now())
        		.build();
	}
//		
	
		
	}

	private List<String> extractsafety(JsonNode safetyNode) {
		List<String> safeties = new ArrayList<>();
		if (safetyNode.isArray()) {
			safetyNode.forEach(safety -> {

				safeties.add(safety.asText());
			});
		}
		return safeties.isEmpty() ? Collections.singletonList("no safety suggested") : safeties;
	}

	private List<String> extractSuggetions(JsonNode suggetionNode) {
		List<String> suggetions = new ArrayList<>();
		if (suggetionNode.isArray()) {
			suggetionNode.forEach(suggetion -> {
				String area = suggetion.path("workout").asText();
				String detail = suggetion.path("description").asText();
				suggetions.add(String.format("%s: %s", area, detail));
			});
		}
		return suggetions.isEmpty() ? Collections.singletonList("no suggetions suggested") : suggetions;
	}

	private List<String> extractImprovements(JsonNode improvementsNode) {
		List<String> improvements = new ArrayList<>();
		if (improvementsNode.isArray()) {
			improvementsNode.forEach(improvement -> {
				String area = improvement.path("area").asText();
				String detail = improvement.path("recomendation").asText();
				improvements.add(String.format("%s: %s", area, detail));
			});
		}
		return improvements.isEmpty() ? Collections.singletonList("no improvements suggested") : improvements;
	}

	private void addAnalysisSection(StringBuilder fullAnalysis, JsonNode analysisNode, String key, String prefix) {
		if (!analysisNode.path(key).isMissingNode()) {
			fullAnalysis.append(prefix).append(analysisNode.path(key).asText()).append("\n\n");

		}

	}

	private String createPrompt(ActivityReq activity) {
		return String.format(
				"""
						Analyze this fitness activity and provide detailed recommendations in the following EXACT JSON format:
						{
						  "analysis": {
						    "overall": "Overall analysis here",
						    "pace": "Pace analysis here",
						    "heartRate": "Heart rate analysis here",
						    "caloriesBurned": "Calories analysis here"
						  },
						  "improvements": [
						    {
						      "area": "Area name",
						      "recommendation": "Detailed recommendation"
						    }
						  ],
						  "suggestions": [
						    {
						      "workout": "Workout name",
						      "description": "Detailed workout description"
						    }
						  ],
						  "safety": [
						    "Safety point 1",
						    "Safety point 2"
						  ]
						}

						Analyze this activity:
						Activity Type: %s
						Duration: %d minutes
						Calories Burned: %d
						Additional Metrics: %s

						Provide detailed analysis focusing on performance, improvements, next workout suggestions, and safety guidelines.
						Ensure the response follows the EXACT JSON format shown above.
						""",
				activity.getType(), activity.getDuration(), activity.getCaloriesBurn(),
				activity.getAdditionalMetrics());
	}

}
