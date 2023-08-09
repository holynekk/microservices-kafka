package com.holynekk.kafka.util;

import com.holynekk.kafka.broker.message.OnlineOrderMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class OnlineOrderTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        OnlineOrderMessage onlineOrderMessage = (OnlineOrderMessage) consumerRecord.value();

        return onlineOrderMessage != null
                ? LocalDateTimeUtil.toEpochTimestamp(onlineOrderMessage.getOrderDateTime())
                : consumerRecord.timestamp();
    }
}
