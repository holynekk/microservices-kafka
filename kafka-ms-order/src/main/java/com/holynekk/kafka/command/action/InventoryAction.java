package com.holynekk.kafka.command.action;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.holynekk.kafka.api.request.InventoryRequest;
import com.holynekk.kafka.broker.message.InventoryMessage;
import com.holynekk.kafka.broker.producer.InventoryProducer;

@Component
public class InventoryAction {

	@Autowired
	private InventoryProducer producer;

	public void publishToKafka(InventoryRequest request, String type) {
		var message = new InventoryMessage();

		message.setLocation(request.getLocation());
		message.setItem(request.getItem());
		message.setQuantity(request.getQuantity());
		message.setType(type);
		message.setTransactionTime(request.getTransactionTime());

		producer.publish(message);
	}

}
