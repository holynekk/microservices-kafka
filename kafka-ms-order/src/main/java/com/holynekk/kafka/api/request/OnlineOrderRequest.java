package com.holynekk.kafka.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class OnlineOrderRequest {

	private String onlineOrderNumber;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime orderDateTime;

	private int totalAmount;
	private String username;

	public String getOnlineOrderNumber() {
		return onlineOrderNumber;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public int getTotalAmount() {
		return totalAmount;
	}

	public String getUsername() {
		return username;
	}

	public void setOnlineOrderNumber(String onlineOrderNumber) {
		this.onlineOrderNumber = onlineOrderNumber;
	}

	public void setOrderDateTime(LocalDateTime orderDateTime) {
		this.orderDateTime = orderDateTime;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "OnlineOrderRequest [onlineOrderNumber=" + onlineOrderNumber + ", orderDateTime=" + orderDateTime
				+ ", totalAmount=" + totalAmount + ", username=" + username + "]";
	}

}
