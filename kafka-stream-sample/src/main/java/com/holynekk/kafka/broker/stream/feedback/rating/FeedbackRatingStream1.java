package com.holynekk.kafka.broker.stream.feedback.rating;

import com.holynekk.kafka.broker.message.FeedbackMessage;
import com.holynekk.kafka.broker.message.FeedbackRatingMessage1;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueBytesStoreSupplier;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class FeedbackRatingStream1 {

    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedbackRating(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<FeedbackMessage> feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        JsonSerde<FeedbackRatingMessage1> feedbackRatingSerde1 = new JsonSerde<>(FeedbackRatingMessage1.class);
        JsonSerde<FeedbackRatingStoreValue1> feedbackRatingStoreValueSerde1 = new JsonSerde<>(FeedbackRatingStoreValue1.class);

        KStream<String, FeedbackMessage> feedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));

        String feedbackRatingStateStoreName = "feedbackRatingStateStore1";
        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
        StoreBuilder<KeyValueStore<String, FeedbackRatingStoreValue1>> storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, feedbackRatingStoreValueSerde1);

        builder.addStateStore(storeBuilder);

        feedbackStream.transformValues(() -> new FeedbackRatingValueTransformer1(feedbackRatingStateStoreName), feedbackRatingStateStoreName)
                .to("t-commodity-feedback-rating-one", Produced.with(stringSerde, feedbackRatingSerde1));

        return feedbackStream;
    }
}
