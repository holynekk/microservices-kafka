package com.holynekk.kafka.broker.stream.feedback.rating;

import com.holynekk.kafka.broker.message.FeedbackMessage;
import com.holynekk.kafka.broker.message.FeedbackRatingMessage1;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Optional;

public class FeedbackRatingValueTransformer1 implements ValueTransformer<FeedbackMessage, FeedbackRatingMessage1> {

    private ProcessorContext processorContext;
    private final String stateStoreName;
    private KeyValueStore<String, FeedbackRatingStoreValue1> ratingStateStore;

    public FeedbackRatingValueTransformer1(String stateStoreName) {
        if (StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("stateStoreName must not empty!");
        }
        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingStoreValue1>) this.processorContext.getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingMessage1 transform(FeedbackMessage feedbackMessage) {
        FeedbackRatingStoreValue1 storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
                .orElse(new FeedbackRatingStoreValue1());

        long newSumRating = storeValue.getSumRating() + feedbackMessage.getRating();
        storeValue.setSumRating(newSumRating);

        long newCountRating = storeValue.getCountRating() + 1;
        storeValue.setCountRating(newCountRating);

        ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

        FeedbackRatingMessage1 branchRating = new FeedbackRatingMessage1();
        branchRating.setLocation(feedbackMessage.getLocation());

        double averageRating = Math.round((double) newSumRating / newCountRating * 10d ) / 10d;
        branchRating.setAverageRating(averageRating);

        return branchRating;
    }

    @Override
    public void close() {

    }
}
