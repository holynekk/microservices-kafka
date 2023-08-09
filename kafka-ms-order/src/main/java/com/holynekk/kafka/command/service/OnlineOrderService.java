package com.holynekk.kafka.command.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.holynekk.kafka.api.request.OnlineOrderRequest;
import com.holynekk.kafka.command.action.OnlineOrderAction;

@Service
public class OnlineOrderService {

	@Autowired
	private OnlineOrderAction action;

	public void saveOnlineOrder(OnlineOrderRequest request) {
		action.publishToKafka(request);
	}

}
