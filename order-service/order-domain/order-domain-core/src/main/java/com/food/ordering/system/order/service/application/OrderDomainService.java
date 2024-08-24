package com.food.ordering.system.order.service.application;

import com.food.ordering.system.order.service.application.entity.Order;
import com.food.ordering.system.order.service.application.entity.Restaurant;
import com.food.ordering.system.order.service.application.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.application.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.application.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage);

    void cancelOrder(Order order,List<String> failureMessage);
}
