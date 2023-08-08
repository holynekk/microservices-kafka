package com.holynekk.kafka.broker.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.holynekk.kafka.broker.message.CustomerPurchaseMobileMessage;
import com.holynekk.kafka.broker.message.CustomerPurchaseWebMessage;

@Service
public class CustomerPurchaseProducer {

	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;

	public void publishPurchaseMobile(CustomerPurchaseMobileMessage message) {
		kafkaTemplate.send("t-commodity-customer-purchase-mobile", message.getPurchaseNumber(), message);
	}

	public void publishPurchaseWeb(CustomerPurchaseWebMessage message) {
		kafkaTemplate.send("t-commodity-customer-purchase-web", message.getPurchaseNumber(), message);
	}

}
