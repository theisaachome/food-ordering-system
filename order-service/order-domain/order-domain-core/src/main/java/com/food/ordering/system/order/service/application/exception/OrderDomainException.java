package com.food.ordering.system.order.service.application.exception;

import com.food.ordering.system.exception.DomainException;

public class OrderDomainException extends DomainException {

    public OrderDomainException(String message) {
        super(message);
    }
    public OrderDomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
