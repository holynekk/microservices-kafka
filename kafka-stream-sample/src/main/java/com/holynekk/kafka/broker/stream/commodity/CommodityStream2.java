package com.holynekk.kafka.broker.stream.commodity;

import com.holynekk.kafka.broker.message.OrderMessage;
import com.holynekk.kafka.broker.message.OrderPatternMessage;
import com.holynekk.kafka.broker.message.OrderRewardMessage;
import com.holynekk.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Branched;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class CommodityStream2 {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<OrderMessage> orderSerde = new JsonSerde<>(OrderMessage.class);
        JsonSerde<OrderPatternMessage> orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        JsonSerde<OrderRewardMessage> orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        // Take t-commodity-order topic as source stream
        KStream<String, OrderMessage> maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

//        // Build another stream (pattern stream) on top of it by mapping its values
//        KStream<String, OrderPatternMessage>[] patternStreams = maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern)
//                .branch(CommodityStreamUtil.isPlastic(), ((k, v) -> true));
//
//        int plasticIndex = 0, notPlasticIndex = 1;
//
//        // Plastic
//        patternStreams[plasticIndex].to("t-commodity-pattern-two-plastic", Produced.with(stringSerde, orderPatternSerde));
//
//        // Not Plastic
//        patternStreams[notPlasticIndex].to("t-commodity-pattern-two-notplastic", Produced.with(stringSerde, orderPatternSerde));

        // Newer syntax for the above commented section
        maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern).split()
                .branch(CommodityStreamUtil.isPlastic(), Branched.withConsumer(ks -> ks.to("to-commodity-pattern-two-plastic", Produced.with(stringSerde, orderPatternSerde))))
                .branch((k, v) -> true, Branched.withConsumer(ks -> ks.to("to-commodity-pattern-two-notplastic", Produced.with(stringSerde, orderPatternSerde))));

        // Build another stream (reward stream) on top of it by mapping its values
        KStream<String, OrderRewardMessage> rewardStream = maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap())
                .mapValues(CommodityStreamUtil::mapToOrderReward);
        rewardStream.to("t-commodity-reward-two", Produced.with(stringSerde, orderRewardSerde));

        // For storage sink processor, don't need to create dedicated stream.
        KStream<String, OrderMessage> storageStream = maskedCreditCardStream.selectKey(CommodityStreamUtil.generateStorageKey());
        storageStream.to("t-commodity-storage-two", Produced.with(stringSerde, orderSerde));

        return maskedCreditCardStream;
    }
}
