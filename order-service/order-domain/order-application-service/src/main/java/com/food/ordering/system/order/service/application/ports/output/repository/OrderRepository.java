package com.food.ordering.system.order.service.application.ports.output.repository;

import com.food.ordering.system.order.service.application.entity.Order;
import com.food.ordering.system.order.service.application.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
