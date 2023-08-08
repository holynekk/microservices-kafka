package com.holynekk.kafka.broker.stream.flashsale;

import com.holynekk.kafka.broker.message.FlashSaleVoteMessage;
import com.holynekk.kafka.util.LocalDateTimeUtil;
import org.apache.kafka.streams.kstream.ValueTransformer;
import org.apache.kafka.streams.processor.ProcessorContext;

import java.time.LocalDateTime;

public class FlashSaleVoteValueTransformer2 implements ValueTransformer<FlashSaleVoteMessage, FlashSaleVoteMessage> {

    private final long voteStartTime;
    private final long voteEndTime;

    private ProcessorContext processorContext;

    public FlashSaleVoteValueTransformer2(LocalDateTime startDateTime, LocalDateTime endDateTime) {
        this.voteStartTime = LocalDateTimeUtil.toEpochTimestamp(startDateTime);
        this.voteEndTime = LocalDateTimeUtil.toEpochTimestamp(endDateTime);
    }

    @Override
    public void init(ProcessorContext processorContext) {
        this.processorContext = processorContext;
    }

    @Override
    public FlashSaleVoteMessage transform(FlashSaleVoteMessage flashSaleVoteMessage) {
        long recordTime = processorContext.timestamp();
        return (recordTime >= voteStartTime && recordTime <= voteEndTime) ? flashSaleVoteMessage : null;
    }

    @Override
    public void close() {

    }
}
