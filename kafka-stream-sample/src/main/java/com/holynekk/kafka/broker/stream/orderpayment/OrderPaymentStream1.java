package com.holynekk.kafka.broker.stream.orderpayment;

import com.holynekk.kafka.broker.message.OnlineOrderMessage;
import com.holynekk.kafka.broker.message.OnlineOrderPaymentMessage;
import com.holynekk.kafka.broker.message.OnlinePaymentMessage;
import com.holynekk.kafka.util.OnlineOrderTimestampExtractor;
import com.holynekk.kafka.util.OnlinePaymentTimestampExtractor;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.time.Duration;

@Configuration
public class OrderPaymentStream1 {

    @Bean
    public KStream<String, OnlineOrderMessage> kstreamOrderPayment(StreamsBuilder builder) {
        Serde<String> stringSerde = Serdes.String();
        JsonSerde<OnlineOrderMessage> orderSerde = new JsonSerde<>(OnlineOrderMessage.class);
        JsonSerde<OnlinePaymentMessage> paymentSerde = new JsonSerde<>(OnlinePaymentMessage.class);
        JsonSerde<OnlineOrderPaymentMessage> orderPaymentSerde = new JsonSerde<>(OnlineOrderPaymentMessage.class);

        KStream<String, OnlineOrderMessage> orderStream = builder.stream("t-commodity-online-order",
                Consumed.with(stringSerde, orderSerde, new OnlineOrderTimestampExtractor(), null));
        KStream<String, OnlinePaymentMessage> paymentStream = builder.stream("t-commodity-online-payment",
                Consumed.with(stringSerde, paymentSerde, new OnlinePaymentTimestampExtractor(), null));

        orderStream
                .join(paymentStream, this::joinOrderPayment, // you can use leftJoin or rightJoin here!!
                JoinWindows.of(Duration.ofHours(1l)).grace(Duration.ofMillis(0l)),
                StreamJoined.with(stringSerde, orderSerde, paymentSerde))
                .to("t-commodity-join-order-payment-one", Produced.with(stringSerde, orderPaymentSerde));

        return orderStream;
    }

    private OnlineOrderPaymentMessage joinOrderPayment(OnlineOrderMessage order, OnlinePaymentMessage payment) {
        OnlineOrderPaymentMessage result = new OnlineOrderPaymentMessage();

        result.setOnlineOrderNumber(order.getOnlineOrderNumber());
        result.setOrderDateTime(order.getOrderDateTime());
        result.setTotalAmount(order.getTotalAmount());
        result.setUsername(order.getUsername());

        result.setPaymentDateTime(payment.getPaymentDateTime());
        result.setPaymentMethod(payment.getPaymentMethod());
        result.setPaymentNumber(payment.getPaymentNumber());

        return result;
    }
}
