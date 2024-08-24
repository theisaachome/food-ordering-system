package com.food.ordering.system.order.service.application.ports.output.message.publisher.payment;

import com.food.ordering.system.event.publisher.DomainEventPublisher;
import com.food.ordering.system.order.service.application.event.OrderCancelledEvent;

public interface OrderCancelledPaymentRequestMessagePublisher extends DomainEventPublisher <OrderCancelledEvent>{
}
