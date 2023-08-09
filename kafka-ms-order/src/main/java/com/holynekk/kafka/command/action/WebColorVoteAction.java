package com.holynekk.kafka.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.holynekk.kafka.api.request.WebColorVoteRequest;
import com.holynekk.kafka.broker.message.WebColorVoteMessage;
import com.holynekk.kafka.broker.producer.WebColorVoteProducer;

@Component
public class WebColorVoteAction {

	@Autowired
	private WebColorVoteProducer producer;

	public void publishToKafka(WebColorVoteRequest request) {
		var message = new WebColorVoteMessage();

		message.setUsername(request.getUsername());
		message.setColor(request.getColor());
		message.setVoteDateTime(request.getVoteDateTime());

		producer.publish(message);
	}

}
