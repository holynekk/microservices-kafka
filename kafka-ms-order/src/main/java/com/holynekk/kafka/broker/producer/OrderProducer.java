package com.holynekk.kafka.broker.producer;

import com.holynekk.kafka.broker.message.OrderMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage orderMessage) {
        kafkaTemplate.send("t-commodity-order", orderMessage.getOrderNumber(), orderMessage);
        LOG.info("Message for order: {}, item: {}", orderMessage.getOrderNumber(), orderMessage.getItemName());

    }
}
