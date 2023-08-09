package com.holynekk.kafka.broker.stream.web;

import com.holynekk.kafka.broker.message.*;
import com.holynekk.kafka.util.WebColorVoteTimestampExtractor;
import com.holynekk.kafka.util.WebLayoutVoteTimestampExtractor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class WebDesignVoteStream1 {
    @Bean
    public KTable<String, WebDesignVoteMessage> kstreamWebDesignVote(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<WebColorVoteMessage> colorSerde = new JsonSerde<>(WebColorVoteMessage.class);
        JsonSerde<WebLayoutVoteMessage> layoutSerde = new JsonSerde<>(WebLayoutVoteMessage.class);
        JsonSerde<WebDesignVoteMessage> designSerde = new JsonSerde<>(WebDesignVoteMessage.class);

        builder.stream("t-commodity-web-vore-color",
                Consumed.with(stringSerde, colorSerde, new WebColorVoteTimestampExtractor(), null))
                .mapValues(v -> v.getColor()).to("t-commodity-web--vote-one-username-color");

        KTable<String, String> colorTable = builder.table("t-commodity-web--vote-one-username-color", Consumed.with(stringSerde, stringSerde));

        builder.stream("t-commodity-web-to-layout", Consumed.with(stringSerde, layoutSerde, new WebLayoutVoteTimestampExtractor(), null))
                .mapValues(v -> v.getLayout()).to("t-commodity-web-vote-one-username-layout");

        KTable<String, String> layoutTable = builder.table("t-commodity-web-vote-one-username-layout", Consumed.with(stringSerde, stringSerde));

        KTable<String, WebDesignVoteMessage> joinTable = colorTable.join(layoutTable, this::voteJoiner);

        joinTable.toStream().to("t-commodity-web-vote-one-result");
        joinTable.groupBy((username, voteDesign) -> KeyValue.pair(voteDesign.getColor(), voteDesign.getColor()))
                .count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote one - color"));
        joinTable.groupBy((username, voteDesign) -> KeyValue.pair(voteDesign.getLayout(), voteDesign.getLayout()))
                .count().toStream().print(Printed.<String, Long>toSysOut().withLabel("Vote one - layout"));

        return joinTable;
    }

    private WebDesignVoteMessage voteJoiner(String color, String layout) {
        WebDesignVoteMessage result = new WebDesignVoteMessage();

        result.setColor(color);
        result.setLayout(layout);

        return result;
    }
}
