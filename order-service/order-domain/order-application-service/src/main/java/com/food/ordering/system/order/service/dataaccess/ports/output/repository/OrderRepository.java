package com.food.ordering.system.order.service.dataaccess.ports.output.repository;

import com.food.ordering.system.order.service.dataaccess.entity.Order;
import com.food.ordering.system.order.service.dataaccess.valueobject.TrackingId;

import java.util.Optional;

public interface OrderRepository {

    Order save(Order order);
    Optional<Order> findByTrackingId(TrackingId trackingId);
}
