package com.holynekk.kafka.broker.stream.customer.preference;

import com.holynekk.kafka.broker.message.CustomerPreferenceAggregateMessage;
import com.holynekk.kafka.broker.message.CustomerPreferenceShoppingCartMessage;
import org.apache.kafka.streams.kstream.Aggregator;

public class CustomerPreferenceShoppingCartAggregator
        implements Aggregator<String, CustomerPreferenceShoppingCartMessage, CustomerPreferenceAggregateMessage> {
    @Override
    public CustomerPreferenceAggregateMessage apply(
            String key, CustomerPreferenceShoppingCartMessage value, CustomerPreferenceAggregateMessage aggregate
    ) {
        aggregate.putShoppingCartItem(value.getItemName(), value.getCartDatetime());
        return aggregate;
    }
}
