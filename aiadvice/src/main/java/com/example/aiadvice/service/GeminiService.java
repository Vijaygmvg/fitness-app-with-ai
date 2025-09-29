package com.example.aiadvice.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service

public class GeminiService {
	
	private final WebClient webClient;
	
	@Value("${gemini.api.key}")
	private String geminiApiKey;
	@Value("${gemini.api.url}")
	private String geminiApiUrl;
	
	public GeminiService(WebClient.Builder webClientBuilder)
	{
		this.webClient=webClientBuilder.build();
	}
	
	public String getRecomendations(String details) {
	    Map<String, Object> request = Map.of(
	        "contents", new Object[] {
	            Map.of("parts", new Object[] {
	                Map.of("text", details)
	            })
	        }
	    );

	    String response = webClient.post()
	        .uri(geminiApiUrl + "?key=" + geminiApiKey)
	        .header("Content-Type", "application/json")
	        .bodyValue(request)
	        .retrieve()
	        .bodyToMono(String.class)
	        .block();

	    return response;
	}
}
