package com.holynekk.kafka.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.holynekk.kafka.api.request.FlashSaleVoteRequest;
import com.holynekk.kafka.broker.message.FlashSaleVoteMessage;
import com.holynekk.kafka.broker.producer.FlashSaleVoteProducer;

@Component
public class FlashSaleVoteAction {

	@Autowired
	private FlashSaleVoteProducer producer;

	public void publishToKafka(FlashSaleVoteRequest request) {
		var message = new FlashSaleVoteMessage();

		message.setCustomerId(request.getCustomerId());
		message.setItemName(request.getItemName());

		producer.publish(message);
	}

}
