package com.example.gateway.user;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserService {

private final WebClient userServiceWebClient;
	
	public Mono<Boolean> validateUser(String userId) {
	
		log.info("calling the userervice for validation user id"+" "+userId);
		try {
		return userServiceWebClient.get().
		 uri("/api/users/{userId}/validate",userId).retrieve()
		 .bodyToMono(Boolean.class)
		 .onErrorResume(WebClientResponseException.class,e->{
			 if(e.getStatusCode()==HttpStatus.NOT_FOUND) {
				 return Mono.error(new RuntimeException("user Not found"+userId));
			 }
			 else if(e.getStatusCode()==HttpStatus.BAD_REQUEST)
				 return Mono.error(new RuntimeException("invalid Bad Request"+userId));
			 return Mono.error(new RuntimeException("unexpected error "+userId));
		 }
		 );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}

	public Mono<UserResponse> registerUser(RegisterRequest registerRequest) {
	
		
		log.info("calinthe registration api ");
	    
	    	return userServiceWebClient.post().
	    			 uri("/api/users/register")
	    			 .bodyValue(registerRequest)
	    			 .retrieve()
	    			 .bodyToMono(UserResponse.class)
	    			 .onErrorResume(WebClientResponseException.class,e->{
	    				
	    				  if(e.getStatusCode()==HttpStatus.BAD_REQUEST)
	    					 return Mono.error(new RuntimeException("invalid Bad Request"+e.getMessage()));
	    				 return Mono.error(new RuntimeException("unexpected error "+e.getMessage()));
	    			 }
	    			 );
	    			}
	    			
	    
	
}
