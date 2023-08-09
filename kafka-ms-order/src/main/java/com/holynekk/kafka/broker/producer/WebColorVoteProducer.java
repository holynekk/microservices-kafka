package com.holynekk.kafka.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.holynekk.kafka.broker.message.WebColorVoteMessage;

@Service
public class WebColorVoteProducer {

	@Autowired
	private KafkaTemplate<String, WebColorVoteMessage> kafkaTemplate;

	public void publish(WebColorVoteMessage message) {
		kafkaTemplate.send("t-commodity-web-vote-color", message.getUsername(), message);
	}

}
