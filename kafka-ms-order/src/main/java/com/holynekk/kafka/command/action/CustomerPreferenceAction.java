package com.holynekk.kafka.command.action;

import com.holynekk.kafka.api.request.CustomerPreferenceShoppingCartRequest;
import com.holynekk.kafka.api.request.CustomerPreferenceWishlistRequest;
import com.holynekk.kafka.broker.message.CustomerPreferenceShoppingCartMessage;
import com.holynekk.kafka.broker.message.CustomerPreferenceWishlistMessage;
import com.holynekk.kafka.broker.producer.CustomerPreferenceProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CustomerPreferenceAction {

	@Autowired
	private CustomerPreferenceProducer producer;

	public void publishShoppingCart(CustomerPreferenceShoppingCartRequest request) {
		var message = new CustomerPreferenceShoppingCartMessage(request.getCustomerId(), request.getItemName(),
				request.getCartAmount(), LocalDateTime.now());

		producer.publishShoppingCart(message);
	}

	public void publishWishlist(CustomerPreferenceWishlistRequest request) {
		var message = new CustomerPreferenceWishlistMessage(request.getCustomerId(), request.getItemName(),
				LocalDateTime.now());

		producer.publishWishlist(message);
	}

}
