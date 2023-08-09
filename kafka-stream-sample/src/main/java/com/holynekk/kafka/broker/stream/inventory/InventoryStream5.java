package com.holynekk.kafka.broker.stream.inventory;

import com.holynekk.kafka.broker.message.InventoryMessage;
import com.holynekk.kafka.util.InventoryTimestampExtractor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

//@Configuration
public class InventoryStream5 {

    @Bean
    public KStream<String, InventoryMessage> kstreamInventory(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<InventoryMessage> inventorySerde = new JsonSerde<>(InventoryMessage.class);
        InventoryTimestampExtractor invetoryTimestampExtractor = new InventoryTimestampExtractor();
        Serde<Long> longSerde = Serdes.Long();

        Duration windowLength = Duration.ofHours(11);
        Serde<Windowed<String>> windowSerde = WindowedSerdes.timeWindowedSerdeFrom(String.class, windowLength.toMillis());


        KStream<String, InventoryMessage> inventoryStream = builder.stream("t-commodity-inventory",
                Consumed.with(stringSerde, inventorySerde, invetoryTimestampExtractor, null));

        inventoryStream.mapValues(
                (k, v) -> v.getType().equalsIgnoreCase("ADD")
                    ? v.getQuantity()
                    : (-1 * v.getQuantity())
        ).groupByKey().windowedBy(TimeWindows.ofSizeWithNoGrace(windowLength))
                .reduce(Long::sum, Materialized.with(stringSerde, longSerde))
                .toStream().through("t-commodity-inventory-five", Produced.with(windowSerde, longSerde))
                .print(Printed.toSysOut());

        return inventoryStream;
    }
}
