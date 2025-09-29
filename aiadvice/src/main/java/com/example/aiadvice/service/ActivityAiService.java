package com.example.aiadvice.service;

import org.springframework.stereotype.Service;

import com.example.aiadvice.model.ActivityReq;
import com.example.aiadvice.model.Recomendation;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j

public class ActivityAiService {
	
	private final GeminiService geminiService;
	
	public Recomendation generateRecomendation(ActivityReq activity) {
		String prompt=createPrompt(activity);
		log.info("the recomendation  for the activity is ");
		//geminiService.getRecomendations(prompt)
		
		
		
		return processAIResponse(geminiService.getRecomendations(prompt));
	}

	private Recomendation processAIResponse(String aiResponse) {
	
	try {
		ObjectMapper mapper=new ObjectMapper();
	  JsonNode rootNode=mapper.readTree(aiResponse);
       String text = rootNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        text=text.replaceAll("'''json\\n", "").replaceAll("\n'''", "").trim();
		for(int i=0;i<5;i++)
		 System.out.println(i);
	
		System.out.println(text);
		JsonNode analysisJson =mapper.readTree(text);
		JsonNode analysisNode=analysisJson.path("analysis");
		
//	)
 	}
		catch(Exception e) {
		e.printStackTrace();
	}
//		
	
		return null;
	}

	private String createPrompt(ActivityReq activity) {
		 return String.format("""
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
			                activity.getType(),
			                activity.getDuration(),
			                activity.getCaloriesBurn(),
			                activity.getAdditionalMetrics()
			        );
	}

}
