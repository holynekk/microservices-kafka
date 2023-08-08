package com.holynekk.kafka.broker.stream.feedback.rating;

import com.holynekk.kafka.broker.message.FeedbackMessage;
import com.holynekk.kafka.broker.message.FeedbackRatingMessage2;
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
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class FeedbackRatingStream2 {

    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedbackRating(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<FeedbackMessage> feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        JsonSerde<FeedbackRatingMessage2> feedbackRatingSerde2 = new JsonSerde<>(FeedbackRatingMessage2.class);
        JsonSerde<FeedbackRatingStoreValue2> feedbackRatingStoreValueSerde2 = new JsonSerde<>(FeedbackRatingStoreValue2.class);

        KStream<String, FeedbackMessage> feedbackStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));

        String feedbackRatingStateStoreName = "feedbackRatingStateStore2";
        KeyValueBytesStoreSupplier storeSupplier = Stores.inMemoryKeyValueStore(feedbackRatingStateStoreName);
        StoreBuilder<KeyValueStore<String, FeedbackRatingStoreValue2>> storeBuilder = Stores.keyValueStoreBuilder(storeSupplier, stringSerde, feedbackRatingStoreValueSerde2);

        builder.addStateStore(storeBuilder);

        feedbackStream.transformValues(() -> new FeedbackRatingValueTransformer2(feedbackRatingStateStoreName), feedbackRatingStateStoreName)
                .to("t-commodity-feedback-rating-two", Produced.with(stringSerde, feedbackRatingSerde2));

        return feedbackStream;
    }
}
