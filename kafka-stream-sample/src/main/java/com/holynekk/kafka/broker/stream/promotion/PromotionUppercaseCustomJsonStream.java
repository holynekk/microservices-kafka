package com.holynekk.kafka.broker.stream.promotion;

import com.holynekk.kafka.broker.message.PromotionMessage;
import com.holynekk.kafka.broker.serde.PromotionSerde;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Printed;
import org.apache.kafka.streams.kstream.Produced;
import org.springframework.context.annotation.Bean;

//@Configuration
public class PromotionUppercaseCustomJsonStream {

	@Bean
	public KStream<String, PromotionMessage> kstreamPromotionUppercase(StreamsBuilder builder) {
		Serde<String> stringSerde = Serdes.String();
		PromotionSerde jsonSerde = new PromotionSerde();
		KStream<String, PromotionMessage> sourceStream = builder.stream("t-commodity-promotion", Consumed.with(stringSerde, jsonSerde));

		KStream<String, PromotionMessage> uppercaseStream = sourceStream.mapValues(this::uppercasePromotionCode);

		uppercaseStream.to("t-commodity-promotion-uppercase", Produced.with(stringSerde, jsonSerde));
		
		sourceStream.print(Printed.<String, PromotionMessage> toSysOut().withLabel("Custom JSON serde original stream"));
		uppercaseStream.print(Printed.<String, PromotionMessage> toSysOut().withLabel("Custom JSON serde uppercase stream"));
		
		return sourceStream;
	}

	public PromotionMessage uppercasePromotionCode(PromotionMessage message) {
		return new PromotionMessage(message.getPromotionCode().toUpperCase());
	}

}
