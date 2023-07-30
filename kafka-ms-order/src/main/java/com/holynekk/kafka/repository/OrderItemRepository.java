package com.holynekk.kafka.repository;

import com.holynekk.kafka.entity.OrderItem;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends CrudRepository<OrderItem, Integer> {
}
