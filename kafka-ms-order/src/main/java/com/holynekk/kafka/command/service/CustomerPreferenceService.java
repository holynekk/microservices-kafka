package com.holynekk.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holynekk.kafka.api.request.CustomerPreferenceShoppingCartRequest;
import com.holynekk.kafka.api.request.CustomerPreferenceWishlistRequest;
import com.holynekk.kafka.command.action.CustomerPreferenceAction;

@Service
public class CustomerPreferenceService {

	@Autowired
	private CustomerPreferenceAction action;

	public void createShoppingCart(CustomerPreferenceShoppingCartRequest request) {
		action.publishShoppingCart(request);
	}

	public void createWishlist(CustomerPreferenceWishlistRequest request) {
		action.publishWishlist(request);
	}
}
