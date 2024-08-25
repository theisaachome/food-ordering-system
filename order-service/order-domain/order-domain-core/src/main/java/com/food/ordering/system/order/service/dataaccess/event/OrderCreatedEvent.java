package com.food.ordering.system.order.service.dataaccess.event;

import com.food.ordering.system.order.service.dataaccess.entity.Order;

import java.time.ZonedDateTime;

public class OrderCreatedEvent extends OrderEvent{
    public OrderCreatedEvent(Order order, ZonedDateTime createdAt) {
        super(order, createdAt);
    }
}
