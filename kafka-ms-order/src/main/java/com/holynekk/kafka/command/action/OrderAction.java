package com.holynekk.kafka.command.action;

import com.holynekk.kafka.api.request.OrderItemRequest;
import com.holynekk.kafka.api.request.OrderRequest;
import com.holynekk.kafka.broker.message.OrderMessage;
import com.holynekk.kafka.broker.producer.OrderProducer;
import com.holynekk.kafka.entity.Order;
import com.holynekk.kafka.entity.OrderItem;
import com.holynekk.kafka.repository.OrderItemRepository;
import com.holynekk.kafka.repository.OrderRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderAction {

    @Autowired
    private OrderProducer orderProducer;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public Order convertToOrder(OrderRequest request) {
        Order order = new Order();

        order.setCreditCardNumber(request.getCreditCardNumber());
        order.setOrderLocation(request.getOrderLocation());
        order.setOrderDateTime(LocalDateTime.now());
        order.setOrderNumber(RandomStringUtils.randomAlphanumeric(8).toUpperCase());

        List<OrderItem> items = request.getItems().stream().map(this::convertToOrderItem).collect(Collectors.toList());
        items.forEach(item -> item.setOrder(order));
        order.setItems(items);

        return order;
    }

    private OrderItem convertToOrderItem(OrderItemRequest itemRequest) {
        OrderItem orderItem = new OrderItem();

        orderItem.setItemName(itemRequest.getItemName());
        orderItem.setPrice(itemRequest.getPrice());
        orderItem.setQuantity(itemRequest.getQuantity());

        return orderItem;
    }

    public void saveToDatabase(Order order) {
        orderRepository.save(order);
        order.getItems().forEach(orderItemRepository::save);
    }

    public void publishToKafka(OrderItem orderItem) {
        OrderMessage orderMessage = new OrderMessage();
        orderMessage.setItemName(orderItem.getItemName());
        orderMessage.setPrice(orderItem.getPrice());
        orderMessage.setQuantity(orderItem.getQuantity());

        Order order = orderItem.getOrder();
        orderMessage.setOrderDateTime(order.getOrderDateTime());
        orderMessage.setOrderLocation(order.getOrderLocation());
        orderMessage.setOrderNumber(order.getOrderNumber());
        orderMessage.setCreditCardNumber(order.getCreditCardNumber());

        orderProducer.publish(orderMessage);
    }

}
