package com.holynekk.kafka.broker.stream.inventory;

import com.holynekk.kafka.broker.message.InventoryMessage;
import com.holynekk.kafka.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

//@Configuration
public class InventoryStream4 {

    @Bean
    public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<InventoryMessage> inventorySerde = new JsonSerde<>(InventoryMessage.class);
        InventoryTimestampExtractor invetoryTimestampExtractor = new InventoryTimestampExtractor();

        KStream<String, InventoryMessage> inventoryStream = builder.stream("t-commodity-inventory",
                Consumed.with(stringSerde, inventorySerde, invetoryTimestampExtractor, null));

        inventoryStream.to("t-commodity-inventory-four", Produced.with(stringSerde, inventorySerde));

        return inventoryStream;
    }
}
