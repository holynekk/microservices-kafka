package com.holynekk.kafka.command.action;

import com.holynekk.kafka.api.request.PromotionRequest;
import com.holynekk.kafka.broker.message.PromotionMessage;
import com.holynekk.kafka.broker.producer.PromotionProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PromotionAction {

    @Autowired
    private PromotionProducer producer;

    public void publishToKafka(PromotionRequest request) {
        PromotionMessage message = new PromotionMessage(request.getPromotionCode());
        producer.publish(message);
    }
}
