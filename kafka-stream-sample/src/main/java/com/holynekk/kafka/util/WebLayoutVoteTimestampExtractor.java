package com.holynekk.kafka.util;

import com.holynekk.kafka.broker.message.WebLayoutVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class WebLayoutVoteTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        WebLayoutVoteMessage message = (WebLayoutVoteMessage) consumerRecord.value();

        return message != null
                ? LocalDateTimeUtil.toEpochTimestamp(message.getVoteDateTime())
                : consumerRecord.timestamp();
    }
}
