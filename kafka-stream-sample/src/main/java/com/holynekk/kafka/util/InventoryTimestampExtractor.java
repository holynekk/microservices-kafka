package com.holynekk.kafka.util;

import com.holynekk.kafka.broker.message.InventoryMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class InventoryTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        InventoryMessage inventoryMessage = (InventoryMessage) consumerRecord.value();
        return inventoryMessage != null
                ? LocalDateTimeUtil.toEpochTimestamp(inventoryMessage.getTransactionTime())
                : consumerRecord.timestamp();
    }
}
