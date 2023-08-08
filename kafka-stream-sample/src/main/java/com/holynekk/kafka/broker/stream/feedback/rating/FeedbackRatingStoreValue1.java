package com.holynekk.kafka.broker.stream.feedback.rating;

public class FeedbackRatingStoreValue1 {

    private long countRating;
    private long sumRating;

    public long getCountRating() {
        return countRating;
    }

    public void setCountRating(long countRating) {
        this.countRating = countRating;
    }

    public long getSumRating() {
        return sumRating;
    }

    public void setSumRating(long sumRating) {
        this.sumRating = sumRating;
    }

    @Override
    public String toString() {
        return "FeedbackRatingStoreValue1{" +
                "countRating=" + countRating +
                ", sumRating=" + sumRating +
                '}';
    }
}
