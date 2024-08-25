package com.food.ordering.system.order.service.dataaccess;

import com.food.ordering.system.order.service.dataaccess.entity.Order;
import com.food.ordering.system.order.service.dataaccess.entity.Restaurant;
import com.food.ordering.system.order.service.dataaccess.event.OrderCancelledEvent;
import com.food.ordering.system.order.service.dataaccess.event.OrderCreatedEvent;
import com.food.ordering.system.order.service.dataaccess.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    OrderCreatedEvent validateAndInitiateOrder(Order order, Restaurant restaurant);
    OrderPaidEvent payOrder(Order order);
    void approveOrder(Order order);
    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessage);

    void cancelOrder(Order order,List<String> failureMessage);
}
