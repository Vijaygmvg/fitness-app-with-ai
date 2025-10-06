package com.example.gateway;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean; // Import for @Bean
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration; // Import for CorsConfiguration
import org.springframework.web.cors.reactive.CorsConfigurationSource; // Import for CorsConfigurationSource
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource; // Import for UrlBasedCorsConfigurationSource

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    // Must be a @Bean method for Spring to configure it as the filter chain
	@Bean
	public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
		
		System.out.println("enter into configutration");
        
        // **Important:** You don't need to manually configure CORS here
        // if you use the CorsConfigurationSource bean below.
        // Spring Security will automatically integrate it.
		return http.csrf(ServerHttpSecurity.CsrfSpec::disable)
				.authorizeExchange(exchange->exchange.anyExchange().authenticated())
				.oauth2ResourceServer(oauth2->oauth2.jwt(Customizer.withDefaults()))
				.build();
	}

    // Add this @Bean method to configure CORS
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		
        // 1. Create a new configuration object
		CorsConfiguration config=new CorsConfiguration();
        
        // 2. Specify the allowed origins (your frontend URL)
		config.setAllowedOrigins(List.of("http://localhost:5173")); 
        
        // 3. Specify the allowed HTTP methods
		config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE", "OPTIONS")); // Include OPTIONS for pre-flight requests
        
        // 4. Specify the allowed headers (use "*" to allow all headers)
		config.addAllowedHeader("*");
        
        // 5. Allow credentials (cookies, authentication headers)
		config.setAllowCredentials(true);
        
        // 6. Create the source object and register the configuration for all paths ("/**")
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        // 7. Return the source
        return source;
	}
}