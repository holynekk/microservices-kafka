package com.holynekk.kafka.broker.stream.commodity;

import com.holynekk.kafka.broker.message.OrderMessage;
import com.holynekk.kafka.broker.message.OrderPatternMessage;
import com.holynekk.kafka.broker.message.OrderRewardMessage;
import com.holynekk.kafka.util.CommodityStreamUtil;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.KafkaStreamBrancher;
import org.springframework.kafka.support.serializer.JsonSerde;


// Alternative for stream branching!!!!
//@Configuration
public class CommodityStream5 {

    @Bean
    public KStream<String, OrderMessage> kstreamCommodityTrading(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<OrderMessage> orderSerde = new JsonSerde<>(OrderMessage.class);
        JsonSerde<OrderPatternMessage> orderPatternSerde = new JsonSerde<>(OrderPatternMessage.class);
        JsonSerde<OrderRewardMessage> orderRewardSerde = new JsonSerde<>(OrderRewardMessage.class);

        // Take t-commodity-order topic as source stream
        KStream<String, OrderMessage> maskedCreditCardStream = builder.stream("t-commodity-order", Consumed.with(stringSerde, orderSerde))
                .mapValues(CommodityStreamUtil::maskCreditCard);

        final Produced<String, OrderPatternMessage> branchProducer = Produced.with(stringSerde, orderPatternSerde);

        new KafkaStreamBrancher<String, OrderPatternMessage>().branch(CommodityStreamUtil.isPlastic(), kstream -> kstream.to("t-commodity-pattern-five-plastic", branchProducer))
                .defaultBranch(kstream -> kstream.to("t-commodity-pattern-five-notplastic", branchProducer))
                .onTopOf(maskedCreditCardStream.mapValues(CommodityStreamUtil::mapToOrderPattern));

        // Build another stream (reward stream) on top of it by mapping its values
        KStream<String, OrderRewardMessage> rewardStream = maskedCreditCardStream.filter(CommodityStreamUtil.isLargeQuantity())
                .filterNot(CommodityStreamUtil.isCheap())
                .map(CommodityStreamUtil.mapToOrderRewardChangeKey());
        rewardStream.to("t-commodity-reward-five", Produced.with(stringSerde, orderRewardSerde));

        // For storage sink processor, don't need to create dedicated stream.
        KStream<String, OrderMessage> storageStream = maskedCreditCardStream.selectKey(CommodityStreamUtil.generateStorageKey());
        storageStream.to("t-commodity-storage-five", Produced.with(stringSerde, orderSerde));

        maskedCreditCardStream.filter((k, v) -> v.getOrderLocation().toUpperCase().startsWith("C")).foreach(((k, v) -> this.reportFraud(v)));

        return maskedCreditCardStream;
    }

    private static final Logger LOG = LoggerFactory.getLogger(CommodityStream5.class);

    private void reportFraud(OrderMessage v) {
        LOG.info("Reporting fraud: {}", v);
    }
}
