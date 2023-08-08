package com.holynekk.kafka.broker.stream.customer.purchase;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.springframework.context.annotation.Bean;

//@Configuration
public class CustomerPurchaseStream1 {

    @Bean
    public KStream<String, String> kStreamCustomerPurchaseAll(StreamsBuilder builder) {
        KStream<String, String> customerPurchaseMobileStream = builder.stream("t-commodity-customer-purchase-mobile",
                Consumed.with(Serdes.String(), Serdes.String()));
        KStream<String, String> customerPurchaseWebStream = builder.stream("t-commodity-customer-purchase-web",
                Consumed.with(Serdes.String(), Serdes.String()));

        customerPurchaseMobileStream.merge(customerPurchaseWebStream).to("t-commodity-customer-purchase-all");

        return customerPurchaseMobileStream;
    }
}
