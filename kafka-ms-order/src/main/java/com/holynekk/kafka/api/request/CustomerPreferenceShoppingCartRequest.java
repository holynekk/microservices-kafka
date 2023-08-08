package com.holynekk.kafka.api.request;

public class CustomerPreferenceShoppingCartRequest {

	private String customerId;
	private String itemName;
	private int cartAmount;

	public CustomerPreferenceShoppingCartRequest() {
	}

	public CustomerPreferenceShoppingCartRequest(String customerId, String itemName, int cartAmount) {
		super();
		this.customerId = customerId;
		this.itemName = itemName;
		this.cartAmount = cartAmount;
	}

	public int getCartAmount() {
		return cartAmount;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setCartAmount(int cartAmount) {
		this.cartAmount = cartAmount;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "CustomerPreferenceShoppingCartRequest [customerId=" + customerId + ", itemName=" + itemName
				+ ", cartAmount=" + cartAmount + "]";
	}

}
