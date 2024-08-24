package com.food.ordering.system.order.service.application.ports.output.message.publisher.restaurantapproval;

import com.food.ordering.system.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.application.event.OrderPaidEvent;

public interface OrderPaidRestaurantRequestMessagePublisher extends DomainEventPublisher<OrderPaidEvent> {
}
