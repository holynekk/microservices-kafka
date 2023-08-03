package com.holynekk.kafka.broker.serde;

import com.holynekk.kafka.broker.message.PromotionMessage;

public class PromotionSerde extends CustomJsonSerde<PromotionMessage> {
    public PromotionSerde() {
        super(new CustomJsonSerializer<PromotionMessage>(), new CustomJsonDeserializer<PromotionMessage>(PromotionMessage.class));
    }
}
