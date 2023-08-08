package com.holynekk.kafka.broker.stream.flashsale;

import com.holynekk.kafka.broker.message.FlashSaleVoteMessage;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class FlashSaleVoteStream1 {
    @Bean
    public KStream<String, String> flashSaleVote(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<FlashSaleVoteMessage> flashSaleVoteSerde = new JsonSerde<>(FlashSaleVoteMessage.class);

        KStream<String, String> flashSaleVoteStream = builder
                .stream("t-commodity-flashsale-vote", Consumed.with(stringSerde, flashSaleVoteSerde))
                .map((key, val) -> KeyValue.pair(val.getCustomerId(), val.getItemName()));

        flashSaleVoteStream.to("t-commodity-flashsale-vote-user-item");

        builder.table("t-commodity-flashsale-vote-user-item", Consumed.with(stringSerde, stringSerde))
                .groupBy((user, votedItem) -> KeyValue.pair(votedItem, votedItem)).count()
                .toStream().to("t-commodity-flashsale-vote-one-result");

        return flashSaleVoteStream;
    }
}
