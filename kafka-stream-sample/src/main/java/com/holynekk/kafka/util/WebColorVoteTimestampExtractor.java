package com.holynekk.kafka.util;

import com.holynekk.kafka.broker.message.WebColorVoteMessage;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.streams.processor.TimestampExtractor;

public class WebColorVoteTimestampExtractor implements TimestampExtractor {
    @Override
    public long extract(ConsumerRecord<Object, Object> consumerRecord, long l) {
        WebColorVoteMessage message = (WebColorVoteMessage) consumerRecord.value();

        return message != null
                ? LocalDateTimeUtil.toEpochTimestamp(message.getVoteDateTime())
                : consumerRecord.timestamp();
    }
}
