package com.holynekk.kafka.broker.message;

import java.util.Map;

public class FeedbackRatingMessage2 {
    private String location;
    private double averageRating;
    private Map<Integer, Long> ratingMap;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public Map<Integer, Long> getRatingMap() {
        return ratingMap;
    }

    public void setRatingMap(Map<Integer, Long> ratingMap) {
        this.ratingMap = ratingMap;
    }

    @Override
    public String toString() {
        return "FeedbackRatingMessage2{" +
                "location='" + location + '\'' +
                ", averageRating=" + averageRating +
                ", ratingMap=" + ratingMap +
                '}';
    }
}
