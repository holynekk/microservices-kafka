package com.holynekk.kafka.broker.message;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class FeedbackMessage {
    private String feedback;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime feedbackDateTime;

    private String location;

    private int rating;

    public String getFeedback() {
        return feedback;
    }

    public LocalDateTime getFeedbackDateTime() {
        return feedbackDateTime;
    }

    public String getLocation() {
        return location;
    }

    public int getRating() {
        return rating;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void setFeedbackDateTime(LocalDateTime feedbackDateTime) {
        this.feedbackDateTime = feedbackDateTime;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "FeedbackMessage{" +
                "feedback='" + feedback + '\'' +
                ", feedbackDateTime=" + feedbackDateTime +
                ", location='" + location + '\'' +
                ", rating=" + rating +
                '}';
    }
}
