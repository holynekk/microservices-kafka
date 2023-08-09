package com.holynekk.kafka.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.holynekk.kafka.api.request.OnlineOrderRequest;
import com.holynekk.kafka.broker.message.OnlineOrderMessage;
import com.holynekk.kafka.broker.producer.OnlineOrderProducer;

@Component
public class OnlineOrderAction {

	@Autowired
	private OnlineOrderProducer producer;

	public void publishToKafka(OnlineOrderRequest request) {
		var message = new OnlineOrderMessage();

		message.setOnlineOrderNumber(request.getOnlineOrderNumber());
		message.setOrderDateTime(request.getOrderDateTime());
		message.setTotalAmount(request.getTotalAmount());
		message.setUsername(request.getUsername().toLowerCase());

		producer.publish(message);
	}

}
