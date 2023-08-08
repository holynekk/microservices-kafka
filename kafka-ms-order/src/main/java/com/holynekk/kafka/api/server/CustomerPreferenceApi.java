package com.holynekk.kafka.api.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.holynekk.kafka.api.request.CustomerPreferenceShoppingCartRequest;
import com.holynekk.kafka.api.request.CustomerPreferenceWishlistRequest;
import com.holynekk.kafka.command.service.CustomerPreferenceService;

@RestController
@RequestMapping("/api/customer/preference")
public class CustomerPreferenceApi {

	@Autowired
	private CustomerPreferenceService service;

	@PostMapping(value = "/shopping-cart", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> createShoppingCart(@RequestBody CustomerPreferenceShoppingCartRequest request) {
		service.createShoppingCart(request);
		
		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Added shopping cart " + request.getItemName() + " for customer " + request.getCustomerId());
	}

	@PostMapping(value = "/wishlist", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public ResponseEntity<String> createWishlist(@RequestBody CustomerPreferenceWishlistRequest request) {
		service.createWishlist(request);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body("Added wishlist " + request.getItemName() + " for customer " + request.getCustomerId());
	}

}
