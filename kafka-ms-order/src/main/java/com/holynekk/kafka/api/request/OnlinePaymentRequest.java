package com.holynekk.kafka.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class OnlinePaymentRequest {

	private String onlineOrderNumber;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime paymentDateTime;

	private String paymentMethod;

	public String getOnlineOrderNumber() {
		return onlineOrderNumber;
	}

	public LocalDateTime getPaymentDateTime() {
		return paymentDateTime;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setOnlineOrderNumber(String onlineOrderNumber) {
		this.onlineOrderNumber = onlineOrderNumber;
	}

	public void setPaymentDateTime(LocalDateTime paymentDateTime) {
		this.paymentDateTime = paymentDateTime;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	@Override
	public String toString() {
		return "OnlinePaymentRequest [onlineOrderNumber=" + onlineOrderNumber + ", paymentDateTime=" + paymentDateTime
				+ ", paymentMethod=" + paymentMethod + "]";
	}

}
