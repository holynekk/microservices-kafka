package com.holynekk.kafka.broker.producer;

import com.holynekk.kafka.broker.message.OrderMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.Header;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderProducer {

    private static final Logger LOG = LoggerFactory.getLogger(OrderProducer.class);

    @Autowired
    private KafkaTemplate<String, OrderMessage> kafkaTemplate;

    public void publish(OrderMessage orderMessage) {
        ProducerRecord<String, OrderMessage> producerRecord = buildProducerRecord(orderMessage);

        kafkaTemplate.send(producerRecord);
        LOG.info("Message for order: {}, item: {}", orderMessage.getOrderNumber(), orderMessage.getItemName());
    }

    private ProducerRecord<String, OrderMessage> buildProducerRecord(OrderMessage message) {
        int surpriseBonus = StringUtils.startsWithIgnoreCase(message.getOrderLocation(), "A") ? 25 : 15;
        List<Header> headers = new ArrayList<>();
        Header surpriseBonusHeader = new RecordHeader("surpriseBonus", Integer.toString(surpriseBonus).getBytes());

        headers.add(surpriseBonusHeader);
        return new ProducerRecord<String, OrderMessage>("t-commodity-order", null, message.getOrderNumber(), message, headers);
    }
}
