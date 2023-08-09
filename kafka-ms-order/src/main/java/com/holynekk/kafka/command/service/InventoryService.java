package com.holynekk.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holynekk.kafka.api.request.InventoryRequest;
import com.holynekk.kafka.command.action.InventoryAction;

@Service
public class InventoryService {

	@Autowired
	private InventoryAction action;

	public void addInventory(InventoryRequest request) {
		action.publishToKafka(request, "ADD");
	}

	public void subtractInventory(InventoryRequest request) {
		action.publishToKafka(request, "REMOVE");
	}

}
