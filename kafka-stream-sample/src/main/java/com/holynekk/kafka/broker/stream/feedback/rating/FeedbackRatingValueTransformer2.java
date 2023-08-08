package com.holynekk.kafka.broker.stream.feedback.rating;

import com.holynekk.kafka.broker.message.FeedbackMessage;
import com.holynekk.kafka.broker.message.FeedbackRatingMessage2;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.state.KeyValueStore;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

public class FeedbackRatingValueTransformer2 implements ValueTransformer<FeedbackMessage, FeedbackRatingMessage2> {

    private ProcessorContext processorContext;
    private final String stateStoreName;
    private KeyValueStore<String, FeedbackRatingStoreValue2> ratingStateStore;

    public FeedbackRatingValueTransformer2(String stateStoreName) {
        if (StringUtils.isEmpty(stateStoreName)) {
            throw new IllegalArgumentException("stateStoreName must not empty!");
        }
        this.stateStoreName = stateStoreName;
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
        this.ratingStateStore = (KeyValueStore<String, FeedbackRatingStoreValue2>) this.processorContext.getStateStore(stateStoreName);
    }

    @Override
    public FeedbackRatingMessage2 transform(FeedbackMessage feedbackMessage) {
        FeedbackRatingStoreValue2 storeValue = Optional.ofNullable(ratingStateStore.get(feedbackMessage.getLocation()))
                .orElse(new FeedbackRatingStoreValue2());
        Map<Integer, Long> ratingMap = Optional.ofNullable(storeValue.getRatingMap()).orElse(new TreeMap<>());

        long currentRatingCount = Optional.ofNullable(ratingMap.get(feedbackMessage.getRating())).orElse(0l);
        long newRatingCount = currentRatingCount + 1;

        ratingMap.put(feedbackMessage.getRating(), newRatingCount);
        ratingStateStore.put(feedbackMessage.getLocation(), storeValue);

        FeedbackRatingMessage2 branchRating = new FeedbackRatingMessage2();
        branchRating.setLocation(feedbackMessage.getLocation());
        branchRating.setRatingMap(ratingMap);
        branchRating.setAverageRating(calculateAverage(ratingMap));

        return branchRating;
    }

    private double calculateAverage(Map<Integer, Long> ratingMap) {
        long sumRating = 0l;
        long countRating = 0l;

        for (Map.Entry<Integer, Long> entry: ratingMap.entrySet()) {
            sumRating += entry.getKey() * entry.getValue();
            countRating += entry.getValue();
        }

        return Math.round((double) sumRating / countRating * 10d) / 10d;
    }

    @Override
    public void close() {

    }
}
