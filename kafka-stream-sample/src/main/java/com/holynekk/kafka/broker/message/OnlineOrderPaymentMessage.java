package com.holynekk.kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class OnlineOrderPaymentMessage {

	private String onlineOrderNumber;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime orderDateTime;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime paymentDateTime;

	private String paymentMethod;
	private String paymentNumber;
	private int totalAmount;
	private String username;

	public String getOnlineOrderNumber() {
		return onlineOrderNumber;
	}

	public LocalDateTime getOrderDateTime() {
		return orderDateTime;
	}

	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public String getPaymentNumber() {
		return paymentNumber;
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

	public void setPaymentDateTime(LocalDateTime paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public void setPaymentNumber(String paymentNumber) {
		this.paymentNumber = paymentNumber;
	}

	public void setTotalAmount(int totalAmount) {
		this.totalAmount = totalAmount;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "OrderPaymentMessage [onlineOrderNumber=" + onlineOrderNumber + ", orderDateTime=" + orderDateTime
				+ ", totalAmount=" + totalAmount + ", username=" + username + ", paymentDateTime=" + paymentDateTime
				+ ", paymentNumber=" + paymentNumber + ", paymentMethod=" + paymentMethod + "]";
	}

}
