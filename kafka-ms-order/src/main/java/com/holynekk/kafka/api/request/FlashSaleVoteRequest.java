package com.holynekk.kafka.api.request;

public class FlashSaleVoteRequest {

	private String customerId;

	private String itemName;

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

	@Override
	public String toString() {
		return "FlashSaleVoteRequest [customerId=" + customerId + ", itemName=" + itemName + "]";
	}

}
