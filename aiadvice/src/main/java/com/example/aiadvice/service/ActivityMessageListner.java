package com.example.aiadvice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.example.aiadvice.model.ActivityReq;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListner {
	
	@Value("${kafka.topic.name}")
	private String topic;
	
	private final ActivityAiService activityAiService;
	@KafkaListener(topics="${kafka.topic.name}",groupId = "${spring.kafka.consumer.group-id}")
	public void processActivity(ActivityReq activity) {
		  
		for(int i=0;i<10;i++)
			 System.out.println(i);
	   System.out.println(activity.toString());
		log.info("consming the insormationof the user "+activity.getUserId());
		activityAiService.generateRecomendation(activity);
	}

}
