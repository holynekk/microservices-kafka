package com.holynekk.kafka.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.holynekk.kafka.broker.message.WebLayoutVoteMessage;

@Service
public class WebLayoutVoteProducer {

	@Autowired
	private KafkaTemplate<String, WebLayoutVoteMessage> kafkaTemplate;

	public void publish(WebLayoutVoteMessage message) {
		kafkaTemplate.send("t-commodity-web-vote-layout", message.getUsername(), message);
	}

}
