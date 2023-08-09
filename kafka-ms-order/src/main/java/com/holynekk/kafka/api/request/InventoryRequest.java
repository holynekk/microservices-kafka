package com.holynekk.kafka.api.request;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class InventoryRequest {

	private String item;
	private long quantity;
	private String location;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime transactionTime;

	public String getItem() {
		return item;
	}

	public String getLocation() {
		return location;
	}

	public long getQuantity() {
		return quantity;
	}

	public LocalDateTime getTransactionTime() {
		return transactionTime;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}

	public void setTransactionTime(LocalDateTime transactionTime) {
		this.transactionTime = transactionTime;
	}

	@Override
	public String toString() {
		return "InventoryRequest [item=" + item + ", quantity=" + quantity + ", location=" + location
				+ ", transactionTime=" + transactionTime + "]";
	}

}
