package com.example.activity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.activity.dto.ActivityRequest;
import com.example.activity.dto.ActivityResponse;
import com.example.activity.service.ActivityService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/activity")
@RequiredArgsConstructor
public class ActivityController {
	
   private final ActivityService activityService;
   
   @PostMapping("/track")
   public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request ,@RequestHeader("X-User-ID") String userId){
	        request.setUserId(userId);
	        System.out.println("callling for the track"+userId);
	       ActivityResponse response=activityService.trackActivity(request);
	         if(response==null)
	        	 return new ResponseEntity(null,HttpStatus.NOT_FOUND);
	         return ResponseEntity.ok(response);
   }
   
   

}
