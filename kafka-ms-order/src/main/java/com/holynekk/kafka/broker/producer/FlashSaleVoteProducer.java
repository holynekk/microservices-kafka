package com.holynekk.kafka.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.holynekk.kafka.broker.message.FlashSaleVoteMessage;

@Service
public class FlashSaleVoteProducer {

	@Autowired
	private KafkaTemplate<String, FlashSaleVoteMessage> kafkaTemplate;

	public void publish(FlashSaleVoteMessage message) {
		kafkaTemplate.send("t-commodity-flashsale-vote", message);
	}

}
