package com.holynekk.kafka.command.service;

import com.holynekk.kafka.api.request.PromotionRequest;
import com.holynekk.kafka.command.action.PromotionAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {
    @Autowired
    private PromotionAction promotionAction;

    public void createPromotion(PromotionRequest request) {
        promotionAction.publishToKafka(request);
    }
}
