package com.holynekk.kafka.command.service;

import com.holynekk.kafka.api.request.FeedbackRequest;
import com.holynekk.kafka.command.action.FeedbackAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {
    @Autowired
    private FeedbackAction action;

    public void createFeedback(FeedbackRequest request) {
        action.publishToKafka(request);
    }
}
