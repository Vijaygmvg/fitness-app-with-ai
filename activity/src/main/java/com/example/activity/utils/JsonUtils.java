package com.example.activity.utils;

import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonUtils {
	  private static final ObjectMapper mapper = new ObjectMapper();

	    public static String mapToJson(Map<String, Object> map) {
	        try {
	            return mapper.writeValueAsString(map);
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException("Failed to convert map to JSON", e);
	        }
	    }

	    public static Map<String, Object> jsonToMap(String json) {
	        try {
	            return mapper.readValue(json, Map.class);
	        } catch (JsonProcessingException e) {
	            throw new RuntimeException("Failed to convert JSON to map", e);
	        }
	    }
}
