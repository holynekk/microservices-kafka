package com.holynekk.kafka.util;

import com.holynekk.kafka.broker.message.OnlinePaymentMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class OnlinePaymentTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        OnlinePaymentMessage onlinePaymentMessage = (OnlinePaymentMessage) consumerRecord.value();

        return onlinePaymentMessage != null
                ? LocalDateTimeUtil.toEpochTimestamp(onlinePaymentMessage.getPaymentDateTime())
                : consumerRecord.timestamp();
    }
}
