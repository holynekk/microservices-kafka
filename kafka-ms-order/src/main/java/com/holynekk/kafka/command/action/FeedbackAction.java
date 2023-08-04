package com.holynekk.kafka.command.action;

import com.holynekk.kafka.api.request.FeedbackRequest;
import com.holynekk.kafka.broker.message.FeedbackMessage;
import com.holynekk.kafka.broker.producer.FeedbackProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class FeedbackAction {
    @Autowired
    private FeedbackProducer producer;

    public void publishToKafka(FeedbackRequest request) {
        var message = new FeedbackMessage();
        message.setFeedback(request.getFeedback());
        message.setLocation(request.getLocation());
        message.setRating(request.getRating());
        // random date time between last 7 days - now
        message.setFeedbackDateTime(LocalDateTime.now().minusHours(ThreadLocalRandom.current().nextLong(7 * 7)));

        producer.publish(message);
    }
}
