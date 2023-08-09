package com.holynekk.kafka.broker.stream.inventory;

import com.holynekk.kafka.broker.message.InventoryMessage;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class InventoryStream1 {

    @Bean
    public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<InventoryMessage> inventorySerde = new JsonSerde<>(InventoryMessage.class);
        Serde<Long> longSerde = Serdes.Long();

        KStream<String, InventoryMessage> inventoryStream = builder.stream("t-commodity-inventory", Consumed.with(stringSerde, inventorySerde));

        inventoryStream.mapValues((item, inventory) -> inventory.getQuantity()).groupByKey().aggregate(
                () -> 0l, (aggKey, newValue, aggValue) -> aggValue + newValue, Materialized.with(stringSerde, longSerde))
                .toStream().to("t-commodity-inventory-total-one", Produced.with(stringSerde, longSerde));

        return inventoryStream;
    }
}
