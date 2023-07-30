package com.holynekk.kafka.broker.consumer;

import com.holynekk.kafka.broker.message.DiscountMessage;
import com.holynekk.kafka.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@KafkaListener(topics = "t-commodity-promotion")
public class PromotionConsumer {
    private static final Logger LOG = LoggerFactory.getLogger(PromotionConsumer.class);

    @KafkaHandler
    public void listenPromotion(PromotionMessage message) {
        LOG.info("Processing promotion: {}", message);
    }

    @KafkaHandler
    public void listenDiscount(DiscountMessage message) {
        LOG.info("Processing discount: {}", message);
    }
}
