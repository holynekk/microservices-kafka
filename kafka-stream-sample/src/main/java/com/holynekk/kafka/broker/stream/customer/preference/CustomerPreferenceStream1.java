package com.holynekk.kafka.broker.stream.customer.preference;

import com.holynekk.kafka.broker.message.CustomerPreferenceAggregateMessage;
import com.holynekk.kafka.broker.message.CustomerPreferenceShoppingCartMessage;
import com.holynekk.kafka.broker.message.CustomerPreferenceWishlistMessage;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

@Configuration
public class CustomerPreferenceStream1 {

    private static final CustomerPreferenceWishlistAggregator WISHLIST_AGGREGATOR = new CustomerPreferenceWishlistAggregator();
    private static final CustomerPreferenceShoppingCartAggregator SHOPPING_CART_AGGREGATOR = new CustomerPreferenceShoppingCartAggregator();

    @Bean
    public KStream<String, CustomerPreferenceAggregateMessage> kstreamCustomerPreferenceAll(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<CustomerPreferenceShoppingCartMessage> shoppingCartSerde = new JsonSerde<>(CustomerPreferenceShoppingCartMessage.class);
        JsonSerde<CustomerPreferenceWishlistMessage> wishlistSerde = new JsonSerde<>(CustomerPreferenceWishlistMessage.class);
        JsonSerde<CustomerPreferenceAggregateMessage> aggregateSerrde = new JsonSerde<>(CustomerPreferenceAggregateMessage.class);

        KGroupedStream<String, CustomerPreferenceShoppingCartMessage> groupedShoppingCartStream = builder.stream("t-commodity-customer-preference-shopping-cart", Consumed.with(stringSerde, shoppingCartSerde)).groupByKey();
        KGroupedStream<String, CustomerPreferenceWishlistMessage> groupedWishlistStream = builder.stream("t-commodity-customer-preference-wishlist", Consumed.with(stringSerde, wishlistSerde)).groupByKey();

        KStream<String, CustomerPreferenceAggregateMessage> customerPreferenceStream = groupedShoppingCartStream.cogroup(SHOPPING_CART_AGGREGATOR).cogroup(groupedWishlistStream, WISHLIST_AGGREGATOR)
                .aggregate(() -> new CustomerPreferenceAggregateMessage(), Materialized.with(stringSerde, aggregateSerrde))
                .toStream();
        customerPreferenceStream.to("t-commodity-customer-preference-all", Produced.with(stringSerde, aggregateSerrde));

        return customerPreferenceStream;
    }
}
