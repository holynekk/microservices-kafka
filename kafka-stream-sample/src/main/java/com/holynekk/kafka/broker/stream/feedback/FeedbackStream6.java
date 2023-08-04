package com.holynekk.kafka.broker.stream.feedback;

import com.holynekk.kafka.broker.message.FeedbackMessage;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Configuration
public class FeedbackStream6 {

    private static final Set<String> GOOD_WORDS = Set.of("happy", "good", "helpful");
    private static final Set<String> BAD_WORDS = Set.of("angry", "sad", "bad");

    @Bean
    public KStream<String, FeedbackMessage> kstreamFeedback(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<FeedbackMessage> feedbackSerde = new JsonSerde<>(FeedbackMessage.class);
        KStream<String, FeedbackMessage> sourceStream = builder.stream("t-commodity-feedback", Consumed.with(stringSerde, feedbackSerde));

        KStream<String, FeedbackMessage>[] feedbackStreams = sourceStream.flatMap(splitWords()).branch(isGoodWord(), isBadWord());

        sourceStream.flatMap(splitWords()).split()
                .branch(isGoodWord(),
                        Branched.withConsumer(ks -> {
                            ks.repartition(Repartitioned.as("t-commodity-feedback-six-good")).groupByKey().count().toStream()
                                    .to("t-commodity-feedback-six-good-count");
                            ks.groupBy((key, val) -> val).count().toStream().to("t-commodity-feedback-six-good-count-word");
                        }))
                .branch(isBadWord(),
                        Branched.withConsumer(ks -> {
                            ks.repartition(Repartitioned.as("t-commodity-feedback-six-bad"))
                                    .groupByKey().count().toStream().to("t-commodity-feedback-six-bad-count");
                            ks.groupBy((key, val) -> val).count().toStream().to("t-commodity-feedback-six-bad-count-word");
                        }));

        return sourceStream;
    }

    private KeyValueMapper<String, FeedbackMessage, Iterable<KeyValue<String, String>>> splitWords() {
        return (key, val) -> Arrays.asList(val.getFeedback().replaceAll("[^a-zA-Z ]", "").toLowerCase().split("\\s+"))
                .stream().distinct().map(word -> KeyValue.pair(val.getLocation(), word)).collect(Collectors.toList());
    }

    private Predicate isBadWord() {
        return (key, val) -> BAD_WORDS.contains(val);
    }

    private Predicate isGoodWord() {
        return (key, val) -> GOOD_WORDS.contains(val);
    }
}
