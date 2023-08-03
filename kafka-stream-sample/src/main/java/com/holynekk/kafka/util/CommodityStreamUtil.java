package com.holynekk.kafka.util;

import com.holynekk.kafka.broker.message.OrderMessage;
import com.holynekk.kafka.broker.message.OrderPatternMessage;
import com.holynekk.kafka.broker.message.OrderRewardMessage;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.streams.kstream.KeyValueMapper;
import org.apache.kafka.streams.kstream.Predicate;

import java.util.Base64;

public class CommodityStreamUtil {
    public static OrderMessage maskCreditCard(OrderMessage original) {
        OrderMessage converted = original.copy();
        String maskCreditCardNumber = original.getCreditCardNumber().replaceFirst("\\d{12}", StringUtils.repeat("*", 12));
        converted.setCreditCardNumber(maskCreditCardNumber);
        return converted;
    }

    public static OrderPatternMessage mapToOrderPattern(OrderMessage original) {
        OrderPatternMessage result = new OrderPatternMessage();

        result.setItemName(original.getItemName());
        result.setOrderLocation(original.getOrderLocation());
        result.setOrderDateTime(original.getOrderDateTime());
        result.setOrderNumber(original.getOrderNumber());
        result.setTotalItemAmount(original.getPrice() * original.getQuantity());

        return result;
    }

    public static OrderRewardMessage mapToOrderReward(OrderMessage original) {
        OrderRewardMessage result = new OrderRewardMessage();

        result.setItemName(original.getItemName());
        result.setOrderLocation(original.getOrderLocation());
        result.setOrderDateTime(original.getOrderDateTime());
        result.setOrderNumber(original.getOrderNumber());
        result.setPrice(original.getPrice());
        result.setQuantity(original.getQuantity());

        return result;
    }

    public static Predicate<String, OrderMessage> isLargeQuantity() {
        return (key, value) -> value.getQuantity() > 200;
    }

    public static Predicate<? super String, ? super OrderPatternMessage> isPlastic() {
        return (key, val) -> StringUtils.startsWithIgnoreCase(val.getItemName(), "plastic");
    }

    public static Predicate<? super String,? super OrderMessage> isCheap() {
        return (key, val) -> val.getPrice() < 100;
    }

    public static KeyValueMapper<String, OrderMessage, String> generateStorageKey() {
        return (key, val) -> Base64.getEncoder().encodeToString(val.getOrderNumber().getBytes());
    }
}
