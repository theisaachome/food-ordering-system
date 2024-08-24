package com.food.ordering.system.order.service.application.ports.output.message.publisher.payment;

import com.food.ordering.system.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.application.event.OrderCreatedEvent;

public interface OrderCreatedPaymentRequestMessagePublisher extends DomainEventPublisher<OrderCreatedEvent> {
}
