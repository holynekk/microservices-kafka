package com.holynekk.kafka.broker.producer;

import com.holynekk.kafka.broker.message.PromotionMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PromotionProducer {

    private static final Logger LOG = LoggerFactory.getLogger(PromotionProducer.class);

    @Autowired
    private KafkaTemplate<String, PromotionMessage> kafkaTemplate;

    public void publish(PromotionMessage message) {
        try {
            SendResult<String, PromotionMessage> sendResult = kafkaTemplate.send("t-commodity-promotion", message).get(); // sync kafka messaging
            LOG.info("Send result success for message: {}", sendResult.getProducerRecord().value());
        } catch (InterruptedException | ExecutionException e) {
            LOG.error("Error publishing: {}, because: {}", message, e.getMessage());
        }
    }
}
