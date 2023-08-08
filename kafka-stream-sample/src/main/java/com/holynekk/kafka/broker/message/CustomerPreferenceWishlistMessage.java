package com.holynekk.kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CustomerPreferenceWishlistMessage {

	private String customerId;
	private String itemName;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime wishlistDatetime;

	public CustomerPreferenceWishlistMessage() {
	}

	public CustomerPreferenceWishlistMessage(String customerId, String itemName, LocalDateTime wishlistDatetime) {
		super();
		this.customerId = customerId;
		this.itemName = itemName;
		this.wishlistDatetime = wishlistDatetime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getItemName() {
		return itemName;
	}

	public LocalDateTime getWishlistDatetime() {
		return wishlistDatetime;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public void setWishlistDatetime(LocalDateTime wishlistDatetime) {
		this.wishlistDatetime = wishlistDatetime;
	}

}
