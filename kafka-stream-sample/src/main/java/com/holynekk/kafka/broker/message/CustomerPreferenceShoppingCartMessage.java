package com.holynekk.kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class CustomerPreferenceShoppingCartMessage {
	
	private String customerId;
	private String itemName;
	private int cartAmount;
	
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime cartDatetime;

	public CustomerPreferenceShoppingCartMessage() {
	}
	
	public CustomerPreferenceShoppingCartMessage(String customerId, String itemName, int cartAmount,
                                                 LocalDateTime cartDatetime) {
		super();
		this.customerId = customerId;
		this.itemName = itemName;
		this.cartAmount = cartAmount;
		this.cartDatetime = cartDatetime;
	}

	public int getCartAmount() {
		return cartAmount;
	}

	public LocalDateTime getCartDatetime() {
		return cartDatetime;
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

	public void setCartDatetime(LocalDateTime cartDatetime) {
		this.cartDatetime = cartDatetime;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Override
	public String toString() {
		return "CustomerPreferenceShoppingCartMessage [customerId=" + customerId + ", itemName=" + itemName
				+ ", cartAmount=" + cartAmount + ", cartDatetime=" + cartDatetime + "]";
	}

}
