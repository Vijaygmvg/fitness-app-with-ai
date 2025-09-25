package com.example.activity.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.Builder;

@Configuration
public class WebClientConfig {

    private final WebClient.Builder webClientBuilder;


    WebClientConfig(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }
	

	@Bean
	@LoadBalanced
	public WebClient.Builder webClientBuilder(){
		return WebClient.builder();
	}
	
	@Bean
	public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
		return webClientBuilder.baseUrl("http://userservice").build();
				
	}
	
	

}
