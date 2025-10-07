package com.example.gateway;

import java.text.ParseException;

import org.springframework.beans.factory.config.RuntimeBeanNameReference;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.example.gateway.user.RegisterRequest;
import com.example.gateway.user.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Component
@Slf4j
@RequiredArgsConstructor
public class KeycloakUserSyncFilter implements WebFilter {
	private final UserService userService;
	
	
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		System.out.println("Enetering the ap gatway security");
	     String userId1 = exchange.getRequest().getHeaders().getFirst("X-User-ID");
	    String token = exchange.getRequest().getHeaders().getFirst("Authorization");
	    RegisterRequest registerRequest = getUserDetails(token);
	    //System.out.println("enter into the filter ");
	    //System.out.println(token);
//	    System.out.println(userId1);
       final String  userId;
       if(userId1!=null) {
    	   userId=userId1;
       }
       else if (userId1 == null && registerRequest != null) {
	        userId=registerRequest.getKeycloakId();
	        
	    }
       else 
    	   return chain.filter(exchange);
       System.out.println("the user id is "+userId);
     
	    if (userId != null && token != null) {
	        return userService.validateUser(userId)
	                .flatMap(exist -> {
	                    if (!exist) {
	                        if (registerRequest != null) {
	                            return userService.registerUser(registerRequest);
	                        } else {
	                            return Mono.empty();
	                        }
	                    } else {
	                        log.info("User already exists, skipping sync.");
	                        return Mono.empty();
	                    }
	                })
	                .then(Mono.defer(() -> {
	                	System.out.println("setting the eader");
	                    ServerHttpRequest mutatedRequest = exchange.getRequest().mutate().header("X-User-ID", userId).build();
	                    return chain.filter(exchange.mutate().request(mutatedRequest).build());
	                }));
	    }

	    // ✅ Fallback: just pass the request down the filter chain
	    return chain.filter(exchange);
	}


	private RegisterRequest getUserDetails(String token)  {
		try {
			System.out.println(token);
			System.out.println("VIjay");
			System.out.println(token.replace("Bearer ", "").trim());
			String tokenWithoutBearer=token.replace("Bearer ", "").trim();
			System.out.println("token without bearer"+tokenWithoutBearer);
			SignedJWT signedJwt=SignedJWT.parse(tokenWithoutBearer);
			JWTClaimsSet claims=signedJwt.getJWTClaimsSet();
			RegisterRequest request=new RegisterRequest();
			request.setEmail(claims.getStringClaim("email"));
			request.setKeycloakId(claims.getStringClaim("sub"));
			request.setFirstName(claims.getStringClaim("given_name"));
			request.setLastName(claims.getStringClaim("family_name"));
			request.setPassword("password@123");
			System.out.println(request);
//			System.out.println("printing thre request");
			return request;
			
		}
		catch (ParseException e) {
			e.printStackTrace();
		  throw new RuntimeException(e);
		}
		
	}

}
