package com.holynekk.kafka.command.service;

import com.holynekk.kafka.api.request.OrderRequest;
import com.holynekk.kafka.command.action.OrderAction;
import com.holynekk.kafka.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    @Autowired
    private OrderAction orderAction;

    public String saveOrder(OrderRequest request) {
        Order order = orderAction.convertToOrder(request);
        orderAction.saveToDatabase(order);

        order.getItems().forEach(orderAction::publishToKafka);

        return order.getOrderNumber();
    }
}
