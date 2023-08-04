package com.holynekk.kafka.api.request;

public class FeedbackRequest {
    private String feedback;

    private String location;

    private int rating;

    public String getFeedback() {
        return feedback;
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

    public void setLocation(String location) {
        this.location = location;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "FeedbackRequest{" +
                "feedback='" + feedback + '\'' +
                ", location='" + location + '\'' +
                ", rating=" + rating +
                '}';
    }
}
