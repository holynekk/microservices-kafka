package com.holynekk.kafka.api.request;

public class CustomerPreferenceWishlistRequest {

	private String customerId;
	private String itemName;

	public CustomerPreferenceWishlistRequest() {
	}

	public CustomerPreferenceWishlistRequest(String customerId, String itemName) {
		super();
		this.customerId = customerId;
		this.itemName = itemName;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

}
