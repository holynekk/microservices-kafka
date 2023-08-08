package com.holynekk.kafka.broker.stream.customer.preference;

import com.holynekk.kafka.broker.message.CustomerPreferenceAggregateMessage;
import com.holynekk.kafka.broker.message.CustomerPreferenceWishlistMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceWishlistAggregator
        implements Aggregator<String, CustomerPreferenceWishlistMessage, CustomerPreferenceAggregateMessage> {

    @Override
    public CustomerPreferenceAggregateMessage apply(
            String key, CustomerPreferenceWishlistMessage value, CustomerPreferenceAggregateMessage aggregate
    ) {
        aggregate.putShoppingCartItem(value.getItemName(), value.getWishlistDatetime());
        return aggregate;
    }
}
